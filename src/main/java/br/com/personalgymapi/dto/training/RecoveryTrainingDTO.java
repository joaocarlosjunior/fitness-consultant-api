package br.com.personalgymapi.dto.training;

import br.com.personalgymapi.dto.musuculegroup.InfoMuscleGroupDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public class RecoveryTrainingDTO {
    @JsonProperty("training_type")
    private String trainingType;

    @JsonProperty("created_at")
    private String created_at;

    @JsonProperty("updated_at")
    private String updated_at;

    @JsonProperty("is_done")
    private boolean is_done;

    @JsonProperty("muscle_groups")
    private List<RecoveryInfoMuscleDTO> muscle_groups;
}
