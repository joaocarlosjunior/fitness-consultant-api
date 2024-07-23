package br.com.fitnessconsultant.dto.exercise;

public record RequestExerciseDTO (
    Long idTraining,

    Integer series,

    String repetitions,

    Integer initialLoad,

    Integer finalLoad,

    String method,

    Long exerciseName
){}
