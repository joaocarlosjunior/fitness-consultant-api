package br.com.fitnessconsultant.dto.user;

public record RequestUserDTO(
        String firstName,
        String lastName,
        String email,
        String phone,
        String password,
        Integer role
) {
}
