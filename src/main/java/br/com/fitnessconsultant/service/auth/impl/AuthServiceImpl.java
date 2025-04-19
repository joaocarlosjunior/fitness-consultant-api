package br.com.fitnessconsultant.service.auth.impl;

import br.com.fitnessconsultant.domain.entities.ConfirmationToken;
import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.repository.ConfirmationTokenRepository;
import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.auth.RequestLoginUserDTO;
import br.com.fitnessconsultant.dto.auth.ResponseJwtTokenDTO;
import br.com.fitnessconsultant.exception.ApiErrorException;
import br.com.fitnessconsultant.exception.InvalidTokenException;
import br.com.fitnessconsultant.service.auth.AuthService;
import br.com.fitnessconsultant.utils.JwtTokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager,
                           JwtTokenUtils jwtTokenUtils,
                           ConfirmationTokenRepository confirmationTokenRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public ResponseEntity<ResponseJwtTokenDTO> authenticate(RequestLoginUserDTO loginUser) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser.email(), loginUser.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(ResponseJwtTokenDTO.builder().token(jwtTokenUtils.generateToken(user)).build());
    }

    @Override
    @Transactional
    public ResponseEntity<Map<String, String>> verify(String verificationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(verificationToken);

        if (Objects.isNull(token)) {
            throw new InvalidTokenException("Token de email não é valido");
        }

        var id = token.getUser().getId();

        User user = userRepository.findById(id).orElseThrow(() -> new ApiErrorException("Não foi possível verificar o e-mail"));

        Map<String, String> response = new HashMap<>();

        if (user.isEnabled()) {
            response.put("message", "Usuário já verificado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        user.setEnabled(true);
        userRepository.save(user);
        confirmationTokenRepository.delete(token);

        response.put("message", "Usuário verificado com sucesso");
        return ResponseEntity.ok(response);
    }
}
