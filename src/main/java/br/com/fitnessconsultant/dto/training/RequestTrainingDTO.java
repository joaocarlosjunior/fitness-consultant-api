package br.com.fitnessconsultant.dto.training;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RequestTrainingDTO(
        @NotNull(message = "Id Periodização é obrigatório")
        @Positive
        Long idPeriodization,
        @NotNull(message = "Treino é obrigatório")
        @Min(message = "Valor minimo training type é 0", value = 0)
        Integer trainingType,
        @NotBlank(message = "Campo nome treino é obrigatório")
        String trainingName
) {
}
