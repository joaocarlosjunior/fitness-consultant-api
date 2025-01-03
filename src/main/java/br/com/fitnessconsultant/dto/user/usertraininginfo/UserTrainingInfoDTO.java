package br.com.fitnessconsultant.dto.user.usertraininginfo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserTrainingInfoDTO(
        Long id,
        String training_name,
        String training_type,
        List<UserExerciseInfoDTO> exercises
){}
