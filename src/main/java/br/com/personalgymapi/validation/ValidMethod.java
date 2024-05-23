package br.com.personalgymapi.validation;

import br.com.personalgymapi.validation.validator.ValidMethodValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ValidMethodValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMethod {
    String message() default "Metodo incorreto inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
