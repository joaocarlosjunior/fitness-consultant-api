package br.com.fitnessconsultant.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateUserDTO(
    String firstName,
    String lastName,
    String email,
    @Pattern(regexp = "^[0-9]+$", message = "Campo telefone deve conter somente números")
    @Length(min = 12, max = 12, message = "Número deve conter 12 digitos")
    String phone
){}
