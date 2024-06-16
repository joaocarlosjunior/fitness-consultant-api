package br.com.fitnessconsultant.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RequestUserDTO (
    @NotBlank(message = "{field.user.first-name}")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ]+$", message = "O campo Nome deve conter apenas letras")
    String firstName,

    @NotBlank(message = "{field.user.last-name}")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ]+$", message = "O campo Sobrenome deve conter apenas letras")
    String lastName,

    @NotBlank(message = "{field.user.email}")
    @Email
    String email,

    @NotBlank(message = "{field.user.phone}")
    @Pattern(regexp = "^[0-9]+$", message = "O campo phone deve conter apenas números")
    String phone,

    @NotBlank(message = "{field.user.password}")
    String password,

    Integer role
){}
