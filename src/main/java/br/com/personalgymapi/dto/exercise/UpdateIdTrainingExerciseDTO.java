package br.com.personalgymapi.dto.exercise;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateIdTrainingExerciseDTO {
    @JsonProperty("id_training")
    @NotNull(message = "Informe id treino")
    private Long idTraining;
}
