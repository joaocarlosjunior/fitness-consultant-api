package br.com.personalgymapi.dto.training;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterTrainingDTO {
    @JsonProperty("id_periodization")
    @NotNull(message = "Campo id user não pode ser nulo")
    private Long idPeriodization;

    @JsonProperty("training_type")
    @NotNull(message = "Campo training_type não pode ser nulo")
    private Integer trainingType;

    @JsonProperty("training_name")
    private String trainingName;
}
