package br.com.fitnessconsultant.dto.training;

import jakarta.validation.constraints.*;

public record RequestUpdateTrainingDTO(
        @Positive
        Long idPeriodization,
        @PositiveOrZero
        Integer trainingType,
        @Size(max = 100, message = "Campo nome treino tem tamanho máximo 100")
        String trainingName
) {
}
