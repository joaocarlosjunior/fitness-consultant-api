package br.com.fitnessconsultant.dto.training;

public record RequestTrainingDTO (
    Long idPeriodization,
    Integer trainingType,
    String trainingName
){}
