package br.com.personalgymapi.validation;

import br.com.personalgymapi.validation.validator.ValidIsActiveValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ValidIsActiveValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIsActive {
    String message() default "Campo Ativo Inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}