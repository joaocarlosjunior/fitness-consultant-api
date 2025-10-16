package br.com.fitnessconsultant.factories;

import br.com.fitnessconsultant.domain.entities.Exercise;
import br.com.fitnessconsultant.dto.exercise.RequestExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.ResponseExerciseDTO;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static br.com.fitnessconsultant.factories.ExerciseNameFactory.createExerciseName;
import static br.com.fitnessconsultant.factories.TrainingFactory.createTraining;

public class ExerciseFactory {

    public static Exercise createExercise() {
        return new Exercise(
                1L,
                1,
                "Repeticoes",
                10,
                20,
                "Metodo",
                createExerciseName(),
                createTraining()
        );
    }

    public static RequestExerciseDTO createExerciseRequest() {
        return new RequestExerciseDTO(
                1L,
                2,
                "Repeticoes teste",
                20,
                35,
                "Metodo teste",
                1L
        );
    }

    public static RequestExerciseDTO createUpdateExerciseRequest() {
        return new RequestExerciseDTO(
                1L,
                4,
                "Atualizado Exercicio",
                20,
                35,
                "metodo teste",
                1L
        );
    }

    public static ResponseExerciseDTO createUpdateExerciseResponse() {
        return new ResponseExerciseDTO(
                1L,
                4,
                "Atualizado Exercicio",
                20,
                35,
                "metodo teste",
                "Nome exercicio"
        );
    }

    public static ResponseExerciseDTO createExerciseResponse() {
        return new ResponseExerciseDTO(
                1L,
                2,
                "Repeticoes teste",
                20,
                35,
                "Metodo teste",
                "Nome do Exercicio"
        );
    }

    public static List<ResponseExerciseDTO> createExerciseList() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> new ResponseExerciseDTO(
                        (long) i,
                        i + 1,
                        "Repeticoes teste " + i,
                        20,
                        35,
                        "Metodo teste " + i,
                        "Nome do Exercicio " + i
                ))
                .collect(Collectors.toList());
    }
}

