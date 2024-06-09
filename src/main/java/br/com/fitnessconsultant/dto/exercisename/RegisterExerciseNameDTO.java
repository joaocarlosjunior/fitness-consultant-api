package br.com.fitnessconsultant.dto.exercisename;

import lombok.Data;

@Data
public class RegisterExerciseNameDTO {
    private String exerciseName;

    private Long idMuscleGroup;
}
