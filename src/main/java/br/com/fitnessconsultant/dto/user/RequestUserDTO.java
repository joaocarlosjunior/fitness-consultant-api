package br.com.fitnessconsultant.dto.user;

import jakarta.validation.constraints.*;

public record RequestUserDTO(
        @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ]+$", message = "O campo nome deve conter apenas letras")
        @NotBlank(message = "Campo nome obrigatório")
        @Size(min = 3, max = 50, message = "Campo nome deve ter tamanho mínimo 3 e máximo 50")
        String firstName,
        @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ]+$", message = "O campo sobrenome deve conter apenas letras")
        @NotBlank(message = "Campo sobrenome obrigatório")
        @Size(min = 3, max = 50, message = "Campo sobrenome deve ter tamanho mínimo 3 e máximo 50")
        String lastName,
        @NotBlank(message = "Campo email obrigatório")
        @Email
        @Size(max = 100, message = "Campo email deve ter tamanho máximo 100")
        String email,
        @Pattern(regexp = "^[0-9]+$", message = "Campo telefone deve conter somente números")
        @Size(min = 11, max = 11, message = "Campo número deve conter 11 digitos")
        @NotBlank(message = "Campo telefone obrigatório")
        String phone,
        @NotNull(message = "Campo role obrigatório")
        @PositiveOrZero
        Integer role
) {
}
