package br.com.personalgymapi.dto.training;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class RecoveryInfoMuscleDTO {
    @JsonProperty("name_muscle_group")
    private String name;
}
