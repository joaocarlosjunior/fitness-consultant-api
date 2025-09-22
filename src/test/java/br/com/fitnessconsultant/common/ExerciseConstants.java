package br.com.fitnessconsultant.common;

import br.com.fitnessconsultant.dto.exercise.RequestExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.ResponseExerciseDTO;

import java.util.List;

public class ExerciseConstants {
    public static final RequestExerciseDTO NEW_EXERCISE =  new RequestExerciseDTO(
            1L,
            2,
            "Repeticoes teste",
            20,
            35,
            "Metodo teste",
            1L
    );

    public static final ResponseExerciseDTO EXERCISE =  new ResponseExerciseDTO(
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
