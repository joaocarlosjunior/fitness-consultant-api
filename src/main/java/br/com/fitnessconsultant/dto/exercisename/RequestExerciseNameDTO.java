package br.com.fitnessconsultant.dto.exercisename;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestExerciseNameDTO (
        @NotBlank(message = "Campo nome exercício é obrigatório")
        String exerciseName,
        @NotNull(message = "Grupo muscular obrigatório")
        Long idMuscleGroup
){}
