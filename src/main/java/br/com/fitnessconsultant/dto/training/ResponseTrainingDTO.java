package br.com.fitnessconsultant.dto.training;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseTrainingDTO {
    private Long idTraining;

    private Long idPeriodization;

    private String trainingType;

    private String trainingName;

    private String createdAt;

    private String updatedAt;
}
