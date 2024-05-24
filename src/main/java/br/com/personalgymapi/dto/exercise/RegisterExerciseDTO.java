package br.com.personalgymapi.dto.exercise;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterExerciseDTO {
    @JsonProperty("series")
    private Integer series;

    @JsonProperty("repetitions")
    private String repetitions;

    @JsonProperty("initial_load")
    private Integer initialLoad;

    @JsonProperty("final_load")
    private Integer finalLoad;

    @JsonProperty("method")
    private String method;

    @JsonProperty("id_training")
    @NotNull(message = "Id Treino obrigatório")
    private Long idTraining;

    @JsonProperty("id_exercise_name")
    @NotNull(message = "Id Nome do Exercício obrigatório")
    private Long exerciseName;
}
