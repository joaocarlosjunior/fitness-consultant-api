package br.com.fitnessconsultant.dto.exercisename;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseExerciseNameDTO {
    private Long idExerciseName;

    private String exerciseName;

    private String muscleGroup;
}