package br.com.fitnessconsultant.dto.exercise;

import jakarta.validation.constraints.NotNull;

public record RequestExerciseDTO (
    @NotNull(message = "Id Treino obrigatório")
    Long idTraining,

    Integer series,

    String repetitions,

    Integer initialLoad,

    Integer finalLoad,

    String method,

    @NotNull(message = "Id Nome do Exercício obrigatório")
    Long exerciseName
){}
