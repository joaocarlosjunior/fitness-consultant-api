package br.com.fitnessconsultant.validation;

import br.com.fitnessconsultant.validation.validator.ValidRoleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ValidRoleValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRole {
    String message() default "Role inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}