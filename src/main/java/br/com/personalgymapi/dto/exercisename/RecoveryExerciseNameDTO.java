package br.com.personalgymapi.dto.exercisename;

import lombok.Builder;

@Builder
public class RecoveryExerciseNameDTO {
    private Long idExerciseName;

    private String exerciseName;

    private String muscleGroup;
}