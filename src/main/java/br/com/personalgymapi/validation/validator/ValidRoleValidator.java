package br.com.personalgymapi.validation.validator;

import br.com.personalgymapi.domain.enums.Role;
import br.com.personalgymapi.validation.ValidRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidRoleValidator implements ConstraintValidator<ValidRole, Role> {

    @Override
    public void initialize(ValidRole constraintAnnotation) {
    }

    @Override
    public boolean isValid(Role role, ConstraintValidatorContext context) {
        return role != null && (role == Role.ROLE_USER || role == Role.ROLE_ADMIN);
    }
}
