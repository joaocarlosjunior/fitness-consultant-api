package br.com.fitnessconsultant.dto.user.infotraininguser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserExerciseInfoDTO {
    private Long id;
    private String name_exercise;
    private Integer series;
    private String repetitions;
    private Integer initial_load;
    private Integer final_load;
    private String method_exercise;
}
