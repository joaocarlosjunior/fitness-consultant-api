package br.com.personalgymapi.validation.validator;

import br.com.personalgymapi.domain.enums.Method;
import br.com.personalgymapi.validation.ValidMethod;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidMethodValidator implements ConstraintValidator<ValidMethod, Method> {
    @Override
    public boolean isValid(Method method, ConstraintValidatorContext constraintValidatorContext) {
        return method != null && (method == Method.SUPER_LEVE ||
                method == Method.SUPER_PESADO
        );
    }
}
