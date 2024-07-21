package br.com.fitnessconsultant.service.user.impl;

import br.com.fitnessconsultant.domain.entities.ConfirmationToken;
import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.repository.ConfirmationTokenRepository;
import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.auth.LoginUserDTO;
import br.com.fitnessconsultant.dto.auth.ResponseJwtTokenDTO;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;
import br.com.fitnessconsultant.exception.EmailVerificationException;
import br.com.fitnessconsultant.exception.InfoAlreadyExistsException;
import br.com.fitnessconsultant.exception.InvalidTokenException;
import br.com.fitnessconsultant.exception.UserNotFoundException;
import br.com.fitnessconsultant.mappers.UserMapper;
import br.com.fitnessconsultant.service.email.EmailService;
import br.com.fitnessconsultant.service.security.JwtService;
import br.com.fitnessconsultant.service.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           ConfirmationTokenRepository confirmationTokenRepository,
                           EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void create(@Valid @NotNull RequestUserDTO requestUserDTO, String siteUrl) {

        boolean emailExists = userRepository
                .existsByEmailIgnoreCase(requestUserDTO.email());

        if (emailExists) {
            throw new InfoAlreadyExistsException("Email já cadastrado");
        }

        boolean phoneExists = userRepository
                .existsByPhone(requestUserDTO.phone());

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
    public ResponseUserDTO findById(@NotNull @Positive Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));

        return userMapper.toDto(user);
    }

    @Transactional
    public void delete(@NotNull @Positive Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        userRepository.deletedById(id);
    }

    @Transactional
    public void setActiveUser(@NotNull @Positive Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));

        if (!user.isActive()) {
            user.setActive(true);
            userRepository.save(user);
        }
    }

    @Transactional
    public void setDisableUser(@NotNull @Positive Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));

        if (user.isActive()) {
            user.setActive(false);
            userRepository.save(user);
        }
    }

    @Transactional
    public ResponseUserDTO update(@NotNull @Positive Long id, @NotNull @Valid UpdateUserDTO updateUserDTO) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));

        String currentEmail = user.getEmail();
        String newEmail = updateUserDTO.email();

        if (!newEmail.equals(currentEmail)) {
            boolean emailExists = userRepository
                    .existsByEmailIgnoreCase(newEmail);

            if (emailExists) {
                throw new InfoAlreadyExistsException("Email já cadastrado");
            }
        }

        String newPhone = updateUserDTO.phone();
        String currentPhone = user.getPhone();

        if (!newPhone.equals(currentPhone)) {
            boolean phoneExists = userRepository
                    .existsByPhone(newPhone);

            if (phoneExists) {
                throw new InfoAlreadyExistsException("Telefone já cadastrado");
            }
        }

        user.setFirstName(updateUserDTO.firstName());
        user.setLastName(updateUserDTO.lastName());
        user.setEmail(updateUserDTO.email());
        user.setPhone(updateUserDTO.phone());

        return userMapper.toDto(userRepository.save(user));
    }

    public ResponseJwtTokenDTO authenticate(@Valid @NotNull LoginUserDTO loginUser) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser.email(), loginUser.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var user = (User) authentication.getPrincipal();

        return ResponseJwtTokenDTO
                .builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    @Transactional(readOnly = true)
    public List<ResponseUserDTO> list() {
        return userRepository
                .findAllUserIsActive(true)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<?> verify(@NotNull String verificationToken){
        ConfirmationToken token = confirmationTokenRepository
                .findByConfirmationToken(verificationToken);

        if(Objects.isNull(token)){
            throw new InvalidTokenException("Token de email não é valido");
        }

        User user = token.getUser();

        if (Objects.isNull(user)){
            return ResponseEntity.badRequest().body("Erro: não foi possível verificar o e-mail");
        }

        user.setEnabled(true);
        userRepository.save(user);
        confirmationTokenRepository.delete(token);

        return ResponseEntity.ok("Email verificado com sucesso!");
    }

}
