package br.com.personalgymapi.dto.exercise;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RegisterExerciseDTO {
    private Integer series;

    private String repetitions;

    private Integer initialLoad;

    private Integer finalLoad;

    private String method;

    @NotNull(message = "Id Treino obrigatório")
    private Long idTraining;

    @NotNull(message = "Id Nome do Exercício obrigatório")
    private Long exerciseName;
}
