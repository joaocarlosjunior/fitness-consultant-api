package br.com.fitnessconsultant.service.exercise;

import br.com.fitnessconsultant.dto.exercise.RecoveryExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.RegisterExerciseDTO;

import java.util.List;

public interface ExerciseService {
    RecoveryExerciseDTO createExercise(RegisterExerciseDTO registerExerciseDTO);

    RecoveryExerciseDTO updateExercise(Long id, RegisterExerciseDTO registerExerciseDTO);

    void deleteExercise(Long id);

    List<RecoveryExerciseDTO> getAllExercisesByIdTraining(Long id);
}
