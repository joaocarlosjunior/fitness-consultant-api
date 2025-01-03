package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.AuthController;
import br.com.fitnessconsultant.dto.auth.LoginUserDTO;
import br.com.fitnessconsultant.dto.auth.ResponseJwtTokenDTO;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.service.auth.AuthService;
import br.com.fitnessconsultant.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthControllerImpl implements AuthController {
    private final UserService userService;
    private final AuthService authService;

    public AuthControllerImpl(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> create(@RequestBody @NotNull RequestUserDTO requestUser,
                                                      HttpServletRequest request){
        userService.create(requestUser, getSiteURL(request));
        Map<String, String> response = new HashMap<>();
        response.put("message","Cheque seu email para verificação da sua conta");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<ResponseJwtTokenDTO> authenticate(@RequestBody @NotNull LoginUserDTO loginUserDTO) {
        return authService.authenticate(loginUserDTO);
    }

    @Override
    @GetMapping("/verify")
    public ResponseEntity<Map<String, String>> verify(@Param("token") @NotNull String token) {
        return authService.verify(token);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
