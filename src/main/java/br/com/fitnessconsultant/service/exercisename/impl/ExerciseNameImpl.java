package br.com.fitnessconsultant.service.exercisename.impl;

import br.com.fitnessconsultant.domain.entities.ExerciseName;
import br.com.fitnessconsultant.domain.entities.MuscleGroup;
import br.com.fitnessconsultant.domain.repository.ExerciseNameRepository;
import br.com.fitnessconsultant.domain.repository.MuscleGroupRepository;
import br.com.fitnessconsultant.dto.exercisename.RecoveryExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.RegisterExerciseNameDTO;
import br.com.fitnessconsultant.exception.InfoAlreadyExistsException;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.service.exercisename.ExerciseNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseNameImpl implements ExerciseNameService {
    private final ExerciseNameRepository exerciseNameRepository;
    private final MuscleGroupRepository muscleGroupRepository;

    @Transactional
    public void createExerciseName(RegisterExerciseNameDTO registerExerciseNameDTO) {
        boolean exerciseNameExists = exerciseNameRepository.
                existsByExerciseNameIgnoreCase(registerExerciseNameDTO.getExerciseName());

        if (exerciseNameExists) {
            throw new InfoAlreadyExistsException("Nome Exercício já existente");
        }

        MuscleGroup muscleGroup = muscleGroupRepository
                .findById(registerExerciseNameDTO.getIdMuscleGroup())
                .orElseThrow(() -> new RecordNotFoundException("Grupo Muscular não encontrado"));

        ExerciseName exerciseName = ExerciseName
                .builder()
                .exerciseName(registerExerciseNameDTO.getExerciseName())
                .muscleGroup(muscleGroup)
                .build();

        exerciseNameRepository.save(exerciseName);
    }

    @Transactional(readOnly = true)
    public RecoveryExerciseNameDTO getExerciseNameById(Long id) {
        ExerciseName exerciseName = exerciseNameRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Nome Exercicio não encontrado"));

        return RecoveryExerciseNameDTO
                .builder()
                .idExerciseName(exerciseName.getId())
                .exerciseName(exerciseName.getExerciseName())
                .muscleGroup(exerciseName.getMuscleGroup().getName())
                .build();
    }

    @Transactional
    public RecoveryExerciseNameDTO updateExerciseNameById(Long id,
                                                          RegisterExerciseNameDTO registerExerciseNameDTO) {
        ExerciseName exerciseName = exerciseNameRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Nome Exercício não encontrado"));

        String newExerciseName = registerExerciseNameDTO.getExerciseName();
        String currentExerciseName = exerciseName.getExerciseName();

        if (!newExerciseName.equalsIgnoreCase(currentExerciseName)) {
            boolean exerciseNameExists = exerciseNameRepository.
                    existsByExerciseNameIgnoreCase(newExerciseName);

            if (exerciseNameExists) {
                throw new InfoAlreadyExistsException("Nome Exercício já existente");
            }
        }

        MuscleGroup muscleGroup = muscleGroupRepository
                .findById(registerExerciseNameDTO.getIdMuscleGroup())
                .orElseThrow(() -> new RecordNotFoundException("Grupo Muscular não encontrado"));

        exerciseName.setExerciseName(registerExerciseNameDTO.getExerciseName());
        exerciseName.setMuscleGroup(muscleGroup);

        ExerciseName updateExerciseName = exerciseNameRepository.save(exerciseName);

        return RecoveryExerciseNameDTO
                .builder()
                .idExerciseName(updateExerciseName.getId())
                .exerciseName(updateExerciseName.getExerciseName())
                .muscleGroup(updateExerciseName.getMuscleGroup().getName())
                .build();
    }

    public void deleteExerciseNameById(Long id) {
        exerciseNameRepository
                .findById(id)
                .map(exerciseName -> {
                    exerciseNameRepository.delete(exerciseName);
                    return Void.class;
                })
                .orElseThrow(() -> new RecordNotFoundException("Nome Exercício não encontrado"));

    }

    public List<RecoveryExerciseNameDTO> getAllExerciseName() {
        List<ExerciseName> exerciseNames = exerciseNameRepository.findAll();

        return exerciseNames
                .stream()
                .map(exerciseName -> RecoveryExerciseNameDTO
                        .builder()
                        .idExerciseName(exerciseName.getId())
                        .exerciseName(exerciseName.getExerciseName())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
