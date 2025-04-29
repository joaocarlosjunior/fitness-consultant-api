package br.com.fitnessconsultant.service.user.impl;

import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.enums.Role;
import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserPeriodizationInfoDTO;
import br.com.fitnessconsultant.exception.SendEmailException;
import br.com.fitnessconsultant.exception.InfoAlreadyExistsException;
import br.com.fitnessconsultant.exception.UserNotFoundException;
import br.com.fitnessconsultant.mappers.UserMapper;
import br.com.fitnessconsultant.service.email.EmailService;
import br.com.fitnessconsultant.service.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.emailService = emailService;
    }

    @Transactional
    public void create(@Valid @NotNull RequestUserDTO requestUserDTO, String siteUrl) {

        boolean emailExists = userRepository.existsByEmailIgnoreCase(requestUserDTO.email());

        if (emailExists) {
            throw new InfoAlreadyExistsException("Email já cadastrado");
        }

        boolean phoneExists = userRepository.existsByPhone(requestUserDTO.phone());

        if (phoneExists) {
            throw new InfoAlreadyExistsException("Telefone já cadastrado");
        }

        String password = generatePassword(6);
        User user = userMapper.toEntity(requestUserDTO, password);
        userRepository.save(user);

        try {
            emailService.sendEmail(user, password);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new SendEmailException("Falha no envio do email");
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseUserDTO> findById(@NotNull @Positive Long id) {
        User user = findUser(id);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @Transactional
    public ResponseEntity<Map<String, String>> setActiveUser(@NotNull @Positive Long id) {
        User user = findUser(id);

        Map<String, String> response = new HashMap<>();

        if (!user.isEnabled()) {
            user.setEnabled(true);
            userRepository.save(user);
            response.put("message", "Usuário ativado com sucesso");
            return ResponseEntity.ok(response);
        }

        response.put("message", "Usuário já ativo");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @Transactional
    public ResponseEntity<Map<String, String>> setDisableUser(@NotNull @Positive Long id) {
        User user = findUser(id);

        Map<String, String> response = new HashMap<>();

        if (user.isEnabled()) {
            user.setEnabled(false);
            userRepository.save(user);
            response.put("message", "Usuário desativado com sucesso");
            return ResponseEntity.ok(response);
        }

        response.put("message", "Usuário já não está ativo");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @Transactional
    public ResponseEntity<ResponseUserDTO> update(@NotNull @Positive Long id, @NotNull @Valid UpdateUserDTO updateUserDTO) {
        User user = findUser(id);

        String currentEmail = user.getEmail();
        String newEmail = updateUserDTO.email();

        if (!newEmail.equals(currentEmail)) {
            boolean emailExists = userRepository.existsByEmailIgnoreCase(newEmail);

            if (emailExists) {
                throw new InfoAlreadyExistsException("Email já cadastrado");
            }
        }

        String newPhone = updateUserDTO.phone();
        String currentPhone = user.getPhone();

        if (!newPhone.equals(currentPhone)) {
            boolean phoneExists = userRepository.existsByPhone(newPhone);

            if (phoneExists) {
                throw new InfoAlreadyExistsException("Telefone já cadastrado");
            }
        }

        user.setFirstName(updateUserDTO.firstName());
        user.setLastName(updateUserDTO.lastName());
        user.setEmail(updateUserDTO.email());
        user.setPhone(updateUserDTO.phone());

        return ResponseEntity.ok(userMapper.toDto(userRepository.save(user)));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ResponseUserDTO>> list() {
        return ResponseEntity.ok(
                userRepository
                .findAllUserRoleUser(Role.ROLE_USER)
                .stream()
                        .map(userMapper::toDto)
                .collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    public ResponseEntity<List<UserPeriodizationInfoDTO>> getAllUserTraining(Long id) {
        if(!userRepository.existsUsersById(id)){
            throw new UserNotFoundException("Usuário não encontrado");
        }

        return ResponseEntity.ok(userRepository.getAllUserTrainingInfo(id));
    }

    private User findUser(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    private String generatePassword(int length){
        final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        return random.ints(length, 0, CARACTERES.length())
                .mapToObj(index -> String.valueOf(CARACTERES.charAt(index)))
                .collect(Collectors.joining());
    }
}
