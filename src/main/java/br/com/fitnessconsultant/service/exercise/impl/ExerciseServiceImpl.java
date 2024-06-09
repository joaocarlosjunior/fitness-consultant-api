package br.com.fitnessconsultant.service.exercise.impl;

import br.com.fitnessconsultant.domain.entities.Exercise;
import br.com.fitnessconsultant.domain.entities.ExerciseName;
import br.com.fitnessconsultant.domain.entities.Training;
import br.com.fitnessconsultant.domain.repository.ExerciseNameRepository;
import br.com.fitnessconsultant.domain.repository.ExerciseRepository;
import br.com.fitnessconsultant.domain.repository.TrainingRepository;
import br.com.fitnessconsultant.dto.exercise.RecoveryExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.RegisterExerciseDTO;
import br.com.fitnessconsultant.service.exercise.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new IllegalArgumentException("Nome de exercício inválido ou inexistente"));

        Training training = trainingRepository
                .findById(registerExerciseDTO.getIdTraining())
                .orElseThrow(() -> new IllegalArgumentException("Treino inválido ou inexistente"));

        Exercise exercise = Exercise
                .builder()
                .exerciseName(exerciseName)
                .finalLoad(registerExerciseDTO.getFinalLoad())
                .initialLoad(registerExerciseDTO.getInitialLoad())
                .repetitions(registerExerciseDTO.getRepetitions())
                .series(registerExerciseDTO.getSeries())
                .training(training)
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
    public RecoveryExerciseDTO updateExercise(Long id, RegisterExerciseDTO registerExerciseDTO) {
        Exercise exercise = exerciseRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercicio inválido ou inexistente"));

        if (!registerExerciseDTO.getExerciseName().equals(exercise.getExerciseName().getId())) {
            ExerciseName exerciseName = exerciseNameRepository
                    .findById(registerExerciseDTO.getExerciseName())
                    .orElseThrow(() -> new IllegalArgumentException("Nome Exercicio inválido ou inexistente"));
            exercise.setExerciseName(exerciseName);
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
                .orElseThrow(() -> new IllegalArgumentException("Exercicio inválido ou inexistente"));
    }

    public List<RecoveryExerciseDTO> getAllExercisesByIdTraining(Long id) {
        trainingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercicio inválido ou inexistente"));

        List<Exercise> exercises = exerciseRepository.getAllExercisesByIdTraining(id);

        return exercises
                .stream()
                .map(exercise -> {
                    return
                            RecoveryExerciseDTO
                                    .builder()
                                    .idExercise(exercise.getId())
                                    .exerciseName(exercise.getExerciseName().getExerciseName())
                                    .initialLoad(exercise.getInitialLoad())
                                    .finalLoad(exercise.getFinalLoad())
                                    .method(exercise.getMethod())
                                    .series(exercise.getSeries())
                                    .build();
                })
                .collect(Collectors.toList());
    }

}
