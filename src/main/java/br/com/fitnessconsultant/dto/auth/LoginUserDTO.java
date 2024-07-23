package br.com.fitnessconsultant.dto.auth;

public record LoginUserDTO(
        String email,
        String password
) {
}
