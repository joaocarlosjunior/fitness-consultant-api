package br.com.fitnessconsultant.dto.training;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestTrainingDTO (
        @NotNull(message = "Periodização é obrigatório")
        Long idPeriodization,
        @NotNull(message = "Treino é obrigatório")
        Integer trainingType,
        @NotBlank(message = "Campo nome treino é obrigatório")
        String trainingName
){}
