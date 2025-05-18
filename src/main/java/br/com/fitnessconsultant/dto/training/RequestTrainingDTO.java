package br.com.fitnessconsultant.dto.training;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RequestTrainingDTO (
        @NotNull(message = "Periodização é obrigatório")
        @Positive
        Long idPeriodization,
        @NotNull(message = "Treino é obrigatório")
        @Positive
        Integer idTrainingType,
        @NotBlank(message = "Campo nome treino é obrigatório")
        String trainingName
){}
