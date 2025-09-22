package br.com.fitnessconsultant.service.auth.impl;

import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.dto.auth.RequestLoginUserDTO;
import br.com.fitnessconsultant.dto.auth.ResponseJwtTokenDTO;
import br.com.fitnessconsultant.service.auth.AuthService;
import br.com.fitnessconsultant.utils.JwtTokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
           JwtTokenUtils jwtTokenUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    public ResponseJwtTokenDTO authenticate(RequestLoginUserDTO loginUser) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser.email(), loginUser.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var user = (User) authentication.getPrincipal();

        return ResponseJwtTokenDTO.builder().token(jwtTokenUtils.generateToken(user)).build();
    }
}
