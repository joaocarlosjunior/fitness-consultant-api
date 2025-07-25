package br.com.fitnessconsultant.dto.training;

import jakarta.validation.constraints.*;

public record RequestTrainingDTO(
        @NotNull(message = "Id Periodização é obrigatório")
        @Positive
        Long idPeriodization,
        @NotNull(message = "Tipo treino é obrigatório")
        @PositiveOrZero
        Integer trainingType,
        @NotBlank(message = "Campo nome treino é obrigatório")
        @Size(max = 100, message = "Campo nome treino tem tamanho máximo 100")
        String trainingName
) {
}
