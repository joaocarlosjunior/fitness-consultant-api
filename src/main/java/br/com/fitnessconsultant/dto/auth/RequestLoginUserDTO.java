package br.com.fitnessconsultant.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RequestLoginUserDTO(
        @NotBlank(message = "Campo email obrigatório")
        String email,
        @NotBlank(message = "Campo senha obrigatório")
        String password
) {}
