package br.com.personalgymapi.validation.validator;

import br.com.personalgymapi.validation.ValidIsActive;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidIsActiveValidator implements ConstraintValidator<ValidIsActive, Boolean> {

    @Override
    public boolean isValid(Boolean aBoolean, ConstraintValidatorContext constraintValidatorContext) {
        return aBoolean != null;
    }
}
