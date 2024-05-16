package br.com.personalgymapi.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterUserDTO {
    @NotBlank(message = "Campo Nome Obrigatório")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "Campo Sobrenome Obrigatório")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message = "Campo email Obrigatório")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Campo Telefone Obrigatório")
    @JsonProperty("phone")
    private String phone;

    @NotBlank(message = "Campo Sobrenome Obrigatório")
    @JsonProperty("password")
    private String password;
}
