package br.com.fitnessconsultant.dto.user.infotraininguser;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserTrainingInfoDTO {
    private Long id;
    private String training_name;
    private String training_type;
    private List<UserExerciseInfoDTO> exercises;
}
