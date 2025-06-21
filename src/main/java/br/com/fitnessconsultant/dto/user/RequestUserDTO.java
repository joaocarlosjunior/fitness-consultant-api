package br.com.fitnessconsultant.dto.user;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public record RequestUserDTO(
        @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ]+$", message = "O campo nome deve conter apenas letras")
        @NotBlank(message = "Campo nome obrigatório")
        String firstName,
        @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ]+$", message = "O campo sobrenome deve conter apenas letras")
        @NotBlank(message = "Campo sobrenome obrigatório")
        String lastName,
        @NotBlank(message = "Campo email obrigatório")
        @Email
        String email,
        @Pattern(regexp = "^[0-9]+$", message = "Campo telefone deve conter somente números")
        @Length(min = 11, max = 11, message = "Número deve conter 11 digitos")
        @NotBlank(message = "Campo telefone obrigatório")
        String phone,
        @NotNull(message = "Campo role obrigatório")
        @Positive
        Integer role
) {
}
