package br.com.fitnessconsultant.dto.auth;

import jakarta.validation.constraints.Email;

public record LoginUserDTO(
        @Email
        String email,
        String password
) {
}
