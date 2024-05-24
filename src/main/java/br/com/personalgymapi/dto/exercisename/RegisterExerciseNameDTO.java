package br.com.personalgymapi.dto.exercisename;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RegisterExerciseNameDTO {
    @JsonProperty("exercise_name")
    private String exerciseName;

    @JsonProperty("id_muscle_group")
    private Long idMuscleGroup;
}
