package br.com.fitnessconsultant.dto.exercisename;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RequestExerciseNameDTO(
        @NotBlank(message = "Campo nome exercício é obrigatório")
        @Size(max = 100, message = "Campo nome exercício tem tamanho máximo 100")
        String exerciseName,
        @NotNull(message = "Id grupo muscular obrigatório")
        @Positive
        Long idMuscleGroup
) {
}
