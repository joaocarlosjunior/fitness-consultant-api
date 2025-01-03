package br.com.fitnessconsultant.service.user.impl;

import br.com.fitnessconsultant.domain.entities.ConfirmationToken;
import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.enums.Role;
import br.com.fitnessconsultant.domain.repository.ConfirmationTokenRepository;
import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserPeriodizationInfoDTO;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserPeriodizationInfodasda;
import br.com.fitnessconsultant.exception.EmailVerificationException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           ConfirmationTokenRepository confirmationTokenRepository,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.confirmationTokenRepository = confirmationTokenRepository;
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

        User user = userMapper.toEntity(requestUserDTO);

        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        try {
            emailService.sendVerificationEmail(user, confirmationToken, siteUrl);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailVerificationException("Falha no envio do email de verificação");
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
}
