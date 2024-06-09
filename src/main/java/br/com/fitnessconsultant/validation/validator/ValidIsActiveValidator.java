package br.com.fitnessconsultant.validation.validator;

import br.com.fitnessconsultant.validation.ValidIsActive;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidIsActiveValidator implements ConstraintValidator<ValidIsActive, Boolean> {

    @Override
    public boolean isValid(Boolean aBoolean, ConstraintValidatorContext constraintValidatorContext) {
        return aBoolean != null;
    }
}
