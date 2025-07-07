package br.com.fitnessconsultant.dto.exercisename;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RequestUpdateExerciseNameDTO(
        @Size(min = 1, max = 100, message = "Campo nome exercício tem tamanho mínimo 1 e máximo 100")
        String exerciseName,
        @Positive
        Long idMuscleGroup
) {
}
