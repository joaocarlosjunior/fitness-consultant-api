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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseExerciseDTO> create(@Valid @NotNull RequestExerciseDTO requestExerciseDTO) {

        ExerciseName exerciseName = exerciseNameRepository
                .getReferenceById(requestExerciseDTO.idExerciseName());

        Training training = trainingRepository
                .getReferenceById(requestExerciseDTO.idTraining());

        return ResponseEntity.status(HttpStatus.CREATED).body(
                exerciseMapper
                        .toDto(exerciseRepository.save(exerciseMapper.toEntity(requestExerciseDTO, exerciseName, training))));
    }

    @Transactional
    public ResponseEntity<ResponseExerciseDTO> update(@NotNull @Positive Long id, @Valid @NotNull RequestExerciseDTO requestExerciseDTO) {
        Exercise exercise = exerciseRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Exercício não encontrado"));

        if (!requestExerciseDTO.idExerciseName().equals(exercise.getExerciseName().getId())) {
            ExerciseName exerciseName = exerciseNameRepository
                    .findById(requestExerciseDTO.idExerciseName()).orElseThrow(() -> new RecordNotFoundException("Id nome de exercicio inválido id: " + requestExerciseDTO.idExerciseName()));
            exercise.setExerciseName(exerciseName);
        }

        if (requestExerciseDTO.method() != null && !requestExerciseDTO.method().isBlank()) {
            exercise.setMethod(requestExerciseDTO.method());
        }

        if (requestExerciseDTO.series() != null) {
            exercise.setSeries(requestExerciseDTO.series());
        }

        if (requestExerciseDTO.finalLoad() != null) {
            exercise.setFinalLoad(requestExerciseDTO.finalLoad());
        }

        if (requestExerciseDTO.initialLoad() != null) {
            exercise.setInitialLoad(requestExerciseDTO.initialLoad());
        }

        if (requestExerciseDTO.repetitions() != null) {
            exercise.setRepetitions(requestExerciseDTO.repetitions());
        }

        return ResponseEntity.ok(exerciseMapper.toDto(exerciseRepository.save(exercise)));
    }

    @Transactional
    public void delete(@NotNull @Positive Long id) {
        exerciseRepository.delete(exerciseRepository
                .getReferenceById(id)
        );
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ResponseExerciseDTO>> getAllExercisesByIdTraining(@NotNull @Positive Long id) {
        if (!trainingRepository.existsTrainingsById(id)) {
            throw new RecordNotFoundException("Exercício não encontrado");
        }

        List<Exercise> exercises = exerciseRepository.getAllExercisesByIdTraining(id);

        return ResponseEntity.ok(
                exercises
                        .stream()
                        .map(exerciseMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

}
