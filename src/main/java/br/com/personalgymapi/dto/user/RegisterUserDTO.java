package br.com.personalgymapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterUserDTO {
    @NotBlank(message = "{field.user.first-name}")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ]+$", message = "O campo Nome deve conter apenas letras")
    private String firstName;

    @NotBlank(message = "{field.user.last-name}")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ]+$", message = "O campo Sobrenome deve conter apenas letras")
    private String lastName;

    @NotBlank(message = "{field.user.email}")
    @Email
    private String email;

    @NotBlank(message = "{field.user.phone}")
    @Pattern(regexp = "^[0-9]+$", message = "O campo phone deve conter apenas números")
    private String phone;

    @NotBlank(message = "{field.user.password}")
    private String password;

    private Integer role;
}
