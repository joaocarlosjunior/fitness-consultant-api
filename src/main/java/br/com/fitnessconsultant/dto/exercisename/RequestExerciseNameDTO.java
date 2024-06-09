package br.com.fitnessconsultant.dto.exercisename;

import lombok.Data;

@Data
public class RequestExerciseNameDTO {
    private String exerciseName;

    private Long idMuscleGroup;
}
