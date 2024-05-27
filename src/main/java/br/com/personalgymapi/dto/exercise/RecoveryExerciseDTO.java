package br.com.personalgymapi.dto.exercise;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecoveryExerciseDTO {
    private Long idExercise;

    private Integer series;

    private String repetitions;

    private Integer initialLoad;

    private Integer finalLoad;

    private String method;

    private String exerciseName;
}
