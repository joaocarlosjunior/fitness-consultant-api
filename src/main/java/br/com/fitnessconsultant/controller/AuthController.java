package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.dto.auth.LoginUserDTO;
import br.com.fitnessconsultant.dto.auth.ResponseJwtTokenDTO;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.exception.ApiErrors;
import br.com.fitnessconsultant.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Cria usuário",
            description = "Cria um usuário"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado um novo usuário"),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid @NotNull RequestUserDTO requestUserDTO, HttpServletRequest request){
        userService.create(requestUserDTO, getSiteURL(request));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseJwtTokenDTO authenticate(@RequestBody @Valid @NotNull LoginUserDTO loginUserDTO) {
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
