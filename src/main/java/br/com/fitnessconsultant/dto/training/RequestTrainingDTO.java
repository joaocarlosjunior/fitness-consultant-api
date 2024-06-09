package br.com.fitnessconsultant.dto.training;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestTrainingDTO {
    @NotNull(message = "id periodização obrigatório")
    private Long idPeriodization;

    @NotNull(message = "Campo Tipo Treino não pode ser nulo")
    private Integer trainingType;

    @NotBlank(message = "Campo Nome Treino obrigatório")
    private String trainingName;
}
