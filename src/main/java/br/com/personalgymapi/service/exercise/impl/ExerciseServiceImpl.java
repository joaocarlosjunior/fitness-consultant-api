package br.com.personalgymapi.service.exercise.impl;

import br.com.personalgymapi.domain.entities.Exercise;
import br.com.personalgymapi.domain.entities.ExerciseName;
import br.com.personalgymapi.domain.entities.Training;
import br.com.personalgymapi.domain.repository.ExerciseNameRepository;
import br.com.personalgymapi.domain.repository.ExerciseRepository;
import br.com.personalgymapi.domain.repository.TrainingRepository;
import br.com.personalgymapi.dto.exercise.RecoveryExerciseDTO;
import br.com.personalgymapi.dto.exercise.RegisterExerciseDTO;
import br.com.personalgymapi.dto.exercise.UpdateIdTrainingExerciseDTO;
import br.com.personalgymapi.service.exercise.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ExerciseNameRepository exerciseNameRepository;
    private final TrainingRepository trainingRepository;

    @Transactional
    public RecoveryExerciseDTO createExercise(RegisterExerciseDTO registerExerciseDTO) {

        ExerciseName exerciseName = exerciseNameRepository
                .findById(registerExerciseDTO.getExerciseName())
                .orElseThrow(() -> new IllegalArgumentException("Nome de exercício não encontrado"));

        Exercise exercise = Exercise
                .builder()
                .exerciseName(exerciseName)
                .finalLoad(registerExerciseDTO.getFinalLoad())
                .initialLoad(registerExerciseDTO.getInitialLoad())
                .repetitions(registerExerciseDTO.getRepetitions())
                .series(registerExerciseDTO.getSeries())
                .training(null)
                .method(registerExerciseDTO.getMethod())
                .build();

        Exercise newExercise = exerciseRepository.save(exercise);

        return RecoveryExerciseDTO
                .builder()
                .idExercise(newExercise.getId())
                .exerciseName(newExercise.getExerciseName().getExerciseName())
                .finalLoad(newExercise.getFinalLoad())
                .initialLoad(newExercise.getInitialLoad())
                .method(newExercise.getMethod())
                .repetitions(newExercise.getRepetitions())
                .series(newExercise.getSeries())
                .build();
    }

    @Transactional
    public void addIdTrainingInExercise(Long id, UpdateIdTrainingExerciseDTO updateIdTrainingExerciseDTO) {
        Exercise exercise = exerciseRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercicio não encontrado"));

        Training training = trainingRepository
                .findById(updateIdTrainingExerciseDTO.getIdTraining())
                .orElseThrow(() -> new IllegalArgumentException("Treino não encontrado"));

        exercise.setTraining(training);

        exerciseRepository.save(exercise);
    }

    @Transactional
    public RecoveryExerciseDTO updateExercise(Long id, RegisterExerciseDTO registerExerciseDTO) {
        Exercise exercise = exerciseRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercicio não encontrado"));

        if (!registerExerciseDTO.getExerciseName().equals(exercise.getExerciseName().getId())) {
            ExerciseName exerciseName = exerciseNameRepository
                    .findById(registerExerciseDTO.getExerciseName())
                    .orElseThrow(() -> new IllegalArgumentException("Nome Exercicio não encontrado"));
            exercise.setExerciseName(exerciseName);
        }

        if (!registerExerciseDTO.getIdTraining().equals(exercise.getTraining().getId())) {
            Training training = trainingRepository
                    .findById(registerExerciseDTO.getIdTraining())
                    .orElseThrow(() -> new IllegalArgumentException("Treino não encontrado"));
            exercise.setTraining(training);
        }

        exercise.setMethod(registerExerciseDTO.getMethod());
        exercise.setSeries(registerExerciseDTO.getSeries());
        exercise.setFinalLoad(registerExerciseDTO.getFinalLoad());
        exercise.setInitialLoad(registerExerciseDTO.getInitialLoad());
        exercise.setRepetitions(registerExerciseDTO.getRepetitions());

        Exercise updateExercise = exerciseRepository.save(exercise);

        return RecoveryExerciseDTO
                .builder()
                .idExercise(updateExercise.getId())
                .series(updateExercise.getSeries())
                .repetitions(updateExercise.getRepetitions())
                .method(updateExercise.getMethod())
                .exerciseName(updateExercise.getExerciseName().getExerciseName())
                .initialLoad(updateExercise.getInitialLoad())
                .finalLoad(updateExercise.getFinalLoad())
                .build();
    }

    @Transactional
    public void deleteExercise(Long id) {
        exerciseRepository
                .findById(id)
                .map(exercise -> {
                    exerciseRepository.delete(exercise);
                    return Void.class;
                })
                .orElseThrow(() -> new IllegalArgumentException("Exercicio não encontrado"));
    }

}
