package br.com.fitnessconsultant.dto.exercise;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RequestExerciseDTO (
        @NotNull(message = "Id treino é obrigatório")
        @Positive
        Long idTraining,
        @Positive
        Integer series,
        String repetitions,
        @Positive
        Integer initialLoad,
        @Positive
        Integer finalLoad,
        String method,
        @NotNull(message = "Nome do exercicio é obrigatório")
        @Positive
        Long idExerciseName
){}
