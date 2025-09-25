package br.com.fitnessconsultant.common;

import br.com.fitnessconsultant.domain.entities.Exercise;
import br.com.fitnessconsultant.dto.exercise.RequestExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.ResponseExerciseDTO;

import java.util.List;

import static br.com.fitnessconsultant.common.ExerciseNameConstants.EXERCISE_NAME;
import static br.com.fitnessconsultant.common.TrainingConstants.TRAINING;

public class ExerciseConstants {
    public static final Exercise EXERCISE = new Exercise(1L, 1, "Repeticoes", 10, 20, "Metodo", EXERCISE_NAME, TRAINING);
    public static final RequestExerciseDTO EXERCISE_REQUEST =  new RequestExerciseDTO(
            1L,
            2,
            "Repeticoes teste",
            20,
            35,
            "Metodo teste",
            1L
    );

    public static final ResponseExerciseDTO EXERCISE_RESPONSE =  new ResponseExerciseDTO(
            1L,
            2,
            "Repeticoes teste",
            20,
            35,
            "Metodo teste",
            "Nome do Exercicio"
    );

    public static final List<ResponseExerciseDTO> LIST_EXERCISES = List.of(
            new ResponseExerciseDTO(
                    1L,
                    2,
                    "Repeticoes teste",
                    20,
                    35,
                    "Metodo teste",
                    "Nome do Exercicio 1"
            ),
            new ResponseExerciseDTO(
                    2L,
                    3,
                    "Repeticoes teste 2",
                    20,
                    35,
                    "Metodo teste 2",
                    "Nome do Exercicio 2"
            ),
            new ResponseExerciseDTO(
                    3L,
                    4,
                    "Repeticoes teste 3",
                    20,
                    35,
                    "Metodo teste 4",
                    "Nome do Exercicio 3"
            )
    );
}
