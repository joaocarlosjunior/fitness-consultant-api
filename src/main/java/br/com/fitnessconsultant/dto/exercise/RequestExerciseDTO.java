package br.com.fitnessconsultant.dto.exercise;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RequestExerciseDTO {
    @NotNull(message = "Id Treino obrigatório")
    private Long idTraining;

    private Integer series;

    private String repetitions;

    private Integer initialLoad;

    private Integer finalLoad;

    private String method;

    @NotNull(message = "Id Nome do Exercício obrigatório")
    private Long exerciseName;
}
