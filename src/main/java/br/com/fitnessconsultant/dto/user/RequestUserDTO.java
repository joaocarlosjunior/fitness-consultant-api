package br.com.fitnessconsultant.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record RequestUserDTO(
        String firstName,
        String lastName,
        String email,
        @Pattern(regexp = "^[0-9]+$", message = "Phone must contain only digits")
        @Length(min = 12, max = 12, message = "Número deve conter 12 digitos")
        String phone,
        String password,
        Integer role
) {
}
