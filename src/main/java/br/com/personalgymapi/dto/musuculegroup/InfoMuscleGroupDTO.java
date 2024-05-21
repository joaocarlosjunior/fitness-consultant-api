package br.com.personalgymapi.dto.musuculegroup;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class InfoMuscleGroupDTO {
    @JsonProperty("id_muscle_group")
    private Long idMuscleGroup;
}
