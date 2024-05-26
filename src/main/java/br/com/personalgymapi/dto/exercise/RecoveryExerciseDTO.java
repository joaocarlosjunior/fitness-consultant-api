package br.com.personalgymapi.dto.exercise;

import lombok.Builder;

@Builder
public class RecoveryExerciseDTO {
    private Integer series;

    private String repetitions;

    private Integer initialLoad;

    private Integer finalLoad;

    private String method;

    private String exerciseName;
}
