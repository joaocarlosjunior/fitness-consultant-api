package br.com.personalgymapi.dto.training;

import lombok.Builder;

@Builder
public class RecoveryTrainingDTO {
    private Long id;

    private String trainingType;

    private String trainingName;

    private String createdAt;

    private String updatedAt;
}
