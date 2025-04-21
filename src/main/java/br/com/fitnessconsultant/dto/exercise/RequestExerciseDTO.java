package br.com.fitnessconsultant.dto.exercise;

import jakarta.validation.constraints.NotNull;

public record RequestExerciseDTO (
        @NotNull(message = "Treino é obrigatório")
        Long idTraining,
        Integer series,
        String repetitions,
        Integer initialLoad,
        Integer finalLoad,
        String method,
        @NotNull(message = "Nome do exercicio é obrigatório")
        Long exerciseName
){}
