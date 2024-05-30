package br.com.personalgymapi.service.exercise;

import br.com.personalgymapi.dto.exercise.RecoveryExerciseDTO;
import br.com.personalgymapi.dto.exercise.RegisterExerciseDTO;
import br.com.personalgymapi.dto.exercise.UpdateIdTrainingExerciseDTO;

import java.util.List;

public interface ExerciseService {
    RecoveryExerciseDTO createExercise(RegisterExerciseDTO registerExerciseDTO);

    void addIdTrainingInExercise(Long id, UpdateIdTrainingExerciseDTO updateIdTrainingExerciseDTO);

    RecoveryExerciseDTO updateExercise(Long id, RegisterExerciseDTO registerExerciseDTO);

    void deleteExercise(Long id);

    List<RecoveryExerciseDTO> getAllExercisesByIdTraining(Long id);
}
