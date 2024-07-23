package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.AuthController;
import br.com.fitnessconsultant.dto.auth.LoginUserDTO;
import br.com.fitnessconsultant.dto.auth.ResponseJwtTokenDTO;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthControllerImpl implements AuthController {
    private final UserService userService;

    public AuthControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @NotNull RequestUserDTO requestUser, HttpServletRequest request){
        userService.create(requestUser, getSiteURL(request));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseJwtTokenDTO authenticate(@RequestBody @NotNull LoginUserDTO loginUserDTO) {
        return userService.authenticate(loginUserDTO);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@Param("token") @NotNull String token) {
        return userService.verify(token);
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
