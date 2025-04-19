package br.com.fitnessconsultant.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record RequestUserDTO(
        @NotBlank(message = "Campo nome obrigatório")
        String firstName,
        @NotBlank(message = "Campo sobrenome obrigatório")
        String lastName,
        @NotBlank(message = "Campo email obrigatório")
        String email,
        @Pattern(regexp = "^[0-9]+$", message = "Campo telefone deve conter somente números")
        @Length(min = 12, max = 12, message = "Número deve conter 12 digitos")
        @NotBlank(message = "Campo telefone obrigatório")
        String phone,
        @NotBlank(message = "Campo senha obrigatório")
        String password,
        @NotNull(message = "Campo role obrigatório")
        Integer role
) {
}
