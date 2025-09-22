package br.com.fitnessconsultant.service.user.impl;

import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.enums.Role;
import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserPeriodizationInfoDTO;
import br.com.fitnessconsultant.exception.InfoAlreadyExistsException;
import br.com.fitnessconsultant.exception.UserNotFoundException;
import br.com.fitnessconsultant.mappers.UserMapper;
import br.com.fitnessconsultant.service.email.EmailService;
import br.com.fitnessconsultant.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
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
    public void create(@Valid @NotNull RequestUserDTO requestUserDTO) {

        boolean emailExists = userRepository.existsByEmailIgnoreCase(requestUserDTO.email());

        if (emailExists) {
            throw new InfoAlreadyExistsException("Email já cadastrado");
        }

        boolean phoneExists = userRepository.existsByPhone(requestUserDTO.phone());

        if (phoneExists) {
            throw new InfoAlreadyExistsException("Telefone já cadastrado");
        }

        //String password = generatePassword(6);
        String password = "12345";
        User user = userMapper.toEntity(requestUserDTO, password);
        userRepository.save(user);

/*        try {
            emailService.sendEmail(user, password);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new SendEmailException("Falha no envio do email");
        }*/
    }

    @Transactional(readOnly = true)
    public ResponseUserDTO findById(@NotNull @Positive Long id) {
        User user = findUser(id);

        return userMapper.toDto(user);
    }

    @Transactional
    public boolean setActiveUser(@NotNull @Positive Long id) {
        User user = findUser(id);

        if (!user.isEnabled()) {
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean setDisableUser(@NotNull @Positive Long id) {
        User user = findUser(id);

        if (user.isEnabled()) {
            user.setEnabled(false);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Transactional
    public ResponseUserDTO update(@NotNull @Positive Long id, @NotNull @Valid UpdateUserDTO updateUserDTO) {
        User user = findUser(id);

        if (updateUserDTO.email() != null && !updateUserDTO.email().equals(user.getEmail())) {
            boolean emailExists = userRepository.existsByEmailIgnoreCase(updateUserDTO.email());

            if (emailExists) {
                throw new InfoAlreadyExistsException("Email já cadastrado");
            }
        }

        if (updateUserDTO.phone() != null && !updateUserDTO.phone().equals(user.getPhone())) {
            boolean phoneExists = userRepository.existsByPhone(updateUserDTO.phone());

            if (phoneExists) {
                throw new InfoAlreadyExistsException("Telefone já cadastrado");
            }
        }

        if (updateUserDTO.firstName() != null && !updateUserDTO.firstName().isBlank()) {
            user.setFirstName(updateUserDTO.firstName());
        }
        if (updateUserDTO.lastName() != null && !updateUserDTO.lastName().isBlank()) {
            user.setLastName(updateUserDTO.lastName());
        }

        if (updateUserDTO.email() != null && !updateUserDTO.email().isBlank()) {
            user.setEmail(updateUserDTO.email());
        }

        if (updateUserDTO.phone() != null && !updateUserDTO.phone().isBlank()) {
            user.setPhone(updateUserDTO.phone());
        }

        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<ResponseUserDTO> list() {
        return
                userRepository
                        .findAllUserRoleUser(Role.ROLE_USER)
                        .stream()
                        .map(userMapper::toDto)
                        .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<UserPeriodizationInfoDTO> getAllUserTraining(Long id) {
        if (!userRepository.existsUsersById(id)) {
            throw new UserNotFoundException("Usuário não encontrado");
        }

        return userRepository.getAllUserTrainingInfo(id);
    }

    private User findUser(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    private String generatePassword(int length) {
        final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        return random.ints(length, 0, CARACTERES.length())
                .mapToObj(index -> String.valueOf(CARACTERES.charAt(index)))
                .collect(Collectors.joining());
    }
}
