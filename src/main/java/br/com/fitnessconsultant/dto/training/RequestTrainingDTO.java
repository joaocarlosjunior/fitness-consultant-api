package br.com.fitnessconsultant.dto.training;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestTrainingDTO (
    @NotNull(message = "id periodização obrigatório")
    Long idPeriodization,

    @NotNull(message = "Campo Tipo Treino não pode ser nulo")
    Integer trainingType,

    @NotBlank(message = "Campo Nome Treino obrigatório")
    String trainingName
){}
