package br.com.personalgymapi.dto.exercise;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateIdTrainingExerciseDTO {
    @NotNull(message = "Informe id treino")
    private Long idTraining;
}
