package br.com.personalgymapi.dto.exercise;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateIdTrainingExerciseDTO {
    @NotNull(message = "Informe id treino")
    private Long idTraining;
}
