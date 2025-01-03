package br.com.fitnessconsultant.dto.user.infotraininguser;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserPeriodizationInfoDTO {
    private Long id;
    private String name;
    private Integer number_weeks;
    private List<UserTrainingInfoDTO> trainings;
}
