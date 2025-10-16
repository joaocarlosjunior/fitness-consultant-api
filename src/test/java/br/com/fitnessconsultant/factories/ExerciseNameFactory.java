package br.com.fitnessconsultant.factories;

import br.com.fitnessconsultant.domain.entities.ExerciseName;
import br.com.fitnessconsultant.dto.exercisename.RequestExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.RequestUpdateExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.ResponseExerciseNameDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExerciseNameFactory {

    public static ExerciseName createExerciseName() {
        return new ExerciseName(1L, "Nome do exercicio", MuscleGroupFactory.createMuscleGroup());
    }

    public static RequestExerciseNameDTO createExerciseNameRequest() {
        return new RequestExerciseNameDTO("Nome exercicio", 1L);
    }

    public static ResponseExerciseNameDTO createExerciseNameResponse() {
        return new ResponseExerciseNameDTO(1L, "Nome do exercício", "Nome do grupo muscular");
    }

    public static RequestUpdateExerciseNameDTO createExerciseNameUpdateRequest() {
        return new RequestUpdateExerciseNameDTO("Nome exercicio atualizado", 1L);
    }

    public static List<ResponseExerciseNameDTO> createExerciseNameList() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> new ResponseExerciseNameDTO(
                        (long) i,
                        "Nome do exercício " + i,
                        "Nome do grupo muscular " + i
                ))
                .collect(Collectors.toList());
    }
}
