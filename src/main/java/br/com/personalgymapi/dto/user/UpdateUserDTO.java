package br.com.personalgymapi.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserDTO {
    @JsonProperty("first_name")
    @Pattern(regexp = "^\\p{L}+$", message = "O campo Sobrenome deve conter apenas letras")
    private String firstName;

    @JsonProperty("last_name")
    @Pattern(regexp = "^\\p{L}+$", message = "O campo Sobrenome deve conter apenas letras")
    private String lastName;

    @JsonProperty("email")
    @Email
    private String email;

    @JsonProperty("phone")
    private String phone;
}
