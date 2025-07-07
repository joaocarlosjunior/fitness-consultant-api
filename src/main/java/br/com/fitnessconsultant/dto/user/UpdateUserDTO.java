package br.com.fitnessconsultant.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateUserDTO(
        @Size(min = 3, max = 50, message = "Campo nome deve ter tamanho mínimo 3 e máximo 50")
        String firstName,
        @Size(min = 3, max = 50, message = "Campo sobrenome deve ter tamanho mínimo 3 e máximo 50")
        String lastName,
        @Email
        @Size(max = 100, message = "Campo email deve ter tamanho máximo 100")
        String email,
        @Pattern(regexp = "^[0-9]+$", message = "Campo telefone deve conter somente números")
        @Size(min = 11, max = 11, message = "Número deve conter 11 digitos")
        String phone
) {
}
