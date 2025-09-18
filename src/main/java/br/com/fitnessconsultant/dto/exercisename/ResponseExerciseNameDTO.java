package br.com.fitnessconsultant.dto.exercisename;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseExerciseNameDTO {
    private Long idExerciseName;

    private String exerciseName;

    private String muscleGroup;
}