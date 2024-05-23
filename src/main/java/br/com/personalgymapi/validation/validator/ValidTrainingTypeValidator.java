package br.com.personalgymapi.validation.validator;

import br.com.personalgymapi.domain.enums.TrainingType;
import br.com.personalgymapi.validation.ValidTrainingType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidTrainingTypeValidator implements ConstraintValidator<ValidTrainingType, TrainingType> {

    @Override
    public boolean isValid(TrainingType trainingType, ConstraintValidatorContext context) {
        return trainingType != null && (trainingType == TrainingType.A ||
                trainingType == TrainingType.B ||
                trainingType == TrainingType.C ||
                trainingType == TrainingType.D ||
                trainingType == TrainingType.E
        );
    }

}
