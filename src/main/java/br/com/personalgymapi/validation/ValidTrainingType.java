package br.com.personalgymapi.validation;

import br.com.personalgymapi.validation.validator.ValidTrainingTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ValidTrainingTypeValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTrainingType {
    String message() default "TrainingType inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
