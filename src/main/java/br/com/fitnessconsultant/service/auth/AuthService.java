package br.com.fitnessconsultant.service.auth;

import br.com.fitnessconsultant.dto.auth.RequestLoginUserDTO;
import br.com.fitnessconsultant.dto.auth.ResponseJwtTokenDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ResponseJwtTokenDTO> authenticate(@Valid @NotNull RequestLoginUserDTO loginUser);
}
