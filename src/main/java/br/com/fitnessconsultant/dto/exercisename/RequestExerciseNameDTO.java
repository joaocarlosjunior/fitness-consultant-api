package br.com.fitnessconsultant.dto.exercisename;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RequestExerciseNameDTO (
        @NotBlank(message = "Campo nome exercício é obrigatório")
        String exerciseName,
        @NotNull(message = "Id grupo muscular obrigatório")
        @Positive
        Long idMuscleGroup
){}
