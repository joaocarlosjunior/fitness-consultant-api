package br.com.personalgymapi.dto.training;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterTrainingDTO {
    @NotNull(message = "Campo id user não pode ser nulo")
    private Long idPeriodization;

    @NotNull(message = "Campo training_type não pode ser nulo")
    private Integer trainingType;

    private String trainingName;
}
