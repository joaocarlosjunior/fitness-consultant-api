package br.com.personalgymapi.dto.exercisename;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class RecoveryExerciseNameDTO {
    @JsonProperty("id_exercise_name")
    private Long idExerciseName;

    @JsonProperty("exercise_name")
    private String exerciseName;

    @JsonProperty("muscle_group")
    private String muscleGroup;
}