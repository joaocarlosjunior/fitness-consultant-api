package br.com.fitnessconsultant.service.exercise.impl;

import br.com.fitnessconsultant.domain.entities.Exercise;
import br.com.fitnessconsultant.domain.entities.ExerciseName;
import br.com.fitnessconsultant.domain.entities.Training;
import br.com.fitnessconsultant.domain.repository.ExerciseNameRepository;
import br.com.fitnessconsultant.domain.repository.ExerciseRepository;
import br.com.fitnessconsultant.domain.repository.TrainingRepository;
import br.com.fitnessconsultant.dto.exercise.ResponseExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.RequestExerciseDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.mappers.ExerciseMapper;
import br.com.fitnessconsultant.service.exercise.ExerciseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseNameRepository exerciseNameRepository;
    private final TrainingRepository trainingRepository;
    private final ExerciseMapper exerciseMapper;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository,
                               ExerciseNameRepository exerciseNameRepository,
                               TrainingRepository trainingRepository,
                               ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseNameRepository = exerciseNameRepository;
        this.trainingRepository = trainingRepository;
        this.exerciseMapper = exerciseMapper;
    }

    @Transactional
    public ResponseExerciseDTO create(@Valid @NotNull RequestExerciseDTO requestExerciseDTO) {

        ExerciseName exerciseName = exerciseNameRepository
                .findById(requestExerciseDTO.exerciseName())
                .orElseThrow(() -> new RecordNotFoundException("Nome de exercício não encontrado"));

        Training training = trainingRepository
                .findById(requestExerciseDTO.idTraining())
                .orElseThrow(() -> new RecordNotFoundException("Treino não encontrado"));

        return exerciseMapper.toDto(exerciseRepository.save(exerciseMapper.toEntity(requestExerciseDTO, exerciseName, training)));
    }

    @Transactional
    public ResponseExerciseDTO update(@NotNull @Positive Long id, @Valid @NotNull RequestExerciseDTO requestExerciseDTO) {
        Exercise exercise = exerciseRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Exercício não encontrado"));

        if (!requestExerciseDTO.exerciseName().equals(exercise.getExerciseName().getId())) {
            ExerciseName exerciseName = exerciseNameRepository
                    .findById(requestExerciseDTO.idTraining())
                    .orElseThrow(() -> new RecordNotFoundException("Nome Exercício não encontrado"));
            exercise.setExerciseName(exerciseName);
        }

        exercise.setMethod(requestExerciseDTO.method());
        exercise.setSeries(requestExerciseDTO.series());
        exercise.setFinalLoad(requestExerciseDTO.finalLoad());
        exercise.setInitialLoad(requestExerciseDTO.initialLoad());
        exercise.setRepetitions(requestExerciseDTO.repetitions());

        return exerciseMapper.toDto(exerciseRepository.save(exercise));
    }

    @Transactional
    public void delete(@NotNull @Positive Long id) {
        exerciseRepository.delete(exerciseRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Exercício não encontrado")));
    }

    @Transactional(readOnly = true)
    public List<ResponseExerciseDTO> getAllExercisesByIdTraining(@NotNull @Positive Long id) {
        trainingRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Exercício não encontrado"));

        List<Exercise> exercises = exerciseRepository.getAllExercisesByIdTraining(id);

        return exercises
                .stream()
                .map(exerciseMapper::toDto)
                .collect(Collectors.toList());
    }

}
