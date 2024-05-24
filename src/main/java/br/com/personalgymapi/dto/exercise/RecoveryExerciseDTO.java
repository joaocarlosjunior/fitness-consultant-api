package br.com.personalgymapi.dto.exercise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class RecoveryExerciseDTO {
    @JsonProperty("series")
    private Integer series;

    @JsonProperty("repetitions")
    private String repetitions;

    @JsonProperty("initial_load")
    private Integer initialLoad;

    @JsonProperty("final_load")
    private Integer finalLoad;

    @JsonProperty("method")
    private String method;

    @JsonProperty("exercise_name")
    private String exerciseName;
}
