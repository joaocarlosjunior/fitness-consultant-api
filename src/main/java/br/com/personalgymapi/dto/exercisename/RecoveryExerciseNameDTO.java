package br.com.personalgymapi.dto.exercisename;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecoveryExerciseNameDTO {
    private Long idExerciseName;

    private String exerciseName;

    private String muscleGroup;
}