package br.com.personalgymapi.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterUserDTO {
    @JsonProperty("first_name")
    @NotBlank(message = "{field.user.first-name}")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ]+$", message = "O campo Nome deve conter apenas letras")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "{field.user.last-name}")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ]+$", message = "O campo Sobrenome deve conter apenas letras")
    private String lastName;

    @JsonProperty("email")
    @NotBlank(message = "{field.user.email}")
    @Email
    private String email;

    @JsonProperty("phone")
    @NotBlank(message = "{field.user.phone}")
    private String phone;

    @JsonProperty("password")
    @NotBlank(message = "{field.user.password}")
    private String password;

    @JsonProperty("role")
    private Integer role;
}
