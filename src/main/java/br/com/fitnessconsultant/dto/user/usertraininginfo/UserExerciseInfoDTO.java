package br.com.fitnessconsultant.dto.user.usertraininginfo;

public record UserExerciseInfoDTO(
        Long id,
        String exercise_name,
        Integer series,
        String repetitions,
        String method_exercise,
        Integer initial_load,
        Integer final_load
){}
