package br.com.personalgymapi.dto.training;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class RecoveryTrainingDTO {
    @JsonProperty("id_training")
    private Long id;

    @JsonProperty("training_type")
    private String trainingType;

    @JsonProperty("training_name")
    private String trainingName;

    @JsonProperty("created_at")
    private String created_at;

    @JsonProperty("updated_at")
    private String updated_at;
}
