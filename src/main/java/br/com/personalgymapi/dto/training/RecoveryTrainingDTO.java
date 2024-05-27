package br.com.personalgymapi.dto.training;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecoveryTrainingDTO {
    private Long idTraining;

    private String trainingType;

    private String trainingName;

    private String createdAt;

    private String updatedAt;
}
