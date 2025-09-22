package br.com.fitnessconsultant.common;

import br.com.fitnessconsultant.dto.exercisename.RequestExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.ResponseExerciseNameDTO;

import java.util.Arrays;
import java.util.List;

public class ExerciseNameConstants {
    public static final RequestExerciseNameDTO EXERCISE_NAME = new RequestExerciseNameDTO("Nome exercício", 1L);
    public static final ResponseExerciseNameDTO EXERCISE_NAME_RESPONSE = new ResponseExerciseNameDTO(1L, "Nome do exercício", "Nome do grupo muscular");

    public static final List<ResponseExerciseNameDTO> EXERCISE_NAME_LIST = Arrays.asList(
            new ResponseExerciseNameDTO(1L, "Nome do exercício 1", "Nome do grupo muscular 1"),
            new ResponseExerciseNameDTO(2L, "Nome do exercício 2", "Nome do grupo muscular 2"),
            new ResponseExerciseNameDTO(3L, "Nome do exercício 3", "Nome do grupo muscular 3")
    );
}
