package br.com.personalgymapi.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterUserDTO {
    @JsonProperty("first_name")
    @NotBlank(message = "Campo Nome Obrigatório")
    @Pattern(regexp = "^\\p{L}+$", message = "O campo Nome deve conter apenas letras")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Campo Sobrenome Obrigatório")
    @Pattern(regexp = "^\\p{L}+$", message = "O campo Sobrenome deve conter apenas letras")
    private String lastName;

    @JsonProperty("email")
    @NotBlank(message = "Campo email Obrigatório")
    @Email
    private String email;

    @JsonProperty("phone")
    @NotBlank(message = "Campo Telefone Obrigatório")
    private String phone;

    @JsonProperty("password")
    @NotBlank(message = "Campo Sobrenome Obrigatório")
    private String password;
}
