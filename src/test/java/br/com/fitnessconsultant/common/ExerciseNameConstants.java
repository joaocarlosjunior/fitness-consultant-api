package br.com.fitnessconsultant.common;

import br.com.fitnessconsultant.domain.entities.ExerciseName;
import br.com.fitnessconsultant.dto.exercisename.RequestExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.ResponseExerciseNameDTO;

import java.util.Arrays;
import java.util.List;

import static br.com.fitnessconsultant.common.MusculeGroupConstants.MUSCLE_GROUP;

public class ExerciseNameConstants {
    public static final ExerciseName EXERCISE_NAME = new ExerciseName(1L, "Nome do exercicio", MUSCLE_GROUP);
    public static final RequestExerciseNameDTO EXERCISE_NAME_REQUEST = new RequestExerciseNameDTO("Nome exercicio", 1L);
    public static final ResponseExerciseNameDTO EXERCISE_NAME_RESPONSE = new ResponseExerciseNameDTO(1L, "Nome do exercício", "Nome do grupo muscular");

    public static final List<ResponseExerciseNameDTO> EXERCISE_NAME_LIST = Arrays.asList(
            new ResponseExerciseNameDTO(1L, "Nome do exercício 1", "Nome do grupo muscular 1"),
            new ResponseExerciseNameDTO(2L, "Nome do exercício 2", "Nome do grupo muscular 2"),
            new ResponseExerciseNameDTO(3L, "Nome do exercício 3", "Nome do grupo muscular 3")
    );
}
