package br.com.personalgymapi.service.exercisename.impl;

import br.com.personalgymapi.domain.entities.ExerciseName;
import br.com.personalgymapi.domain.entities.MuscleGroup;
import br.com.personalgymapi.domain.repository.ExerciseNameRepository;
import br.com.personalgymapi.domain.repository.MuscleGroupRepository;
import br.com.personalgymapi.dto.exercisename.RecoveryExerciseNameDTO;
import br.com.personalgymapi.dto.exercisename.RegisterExerciseNameDTO;
import br.com.personalgymapi.exception.InfoAlreadyExistsException;
import br.com.personalgymapi.service.exercisename.ExerciseNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new IllegalArgumentException("Grupo Muscular inválido"));

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
                .orElseThrow(() -> new IllegalArgumentException("Nome Exercicio não existe"));

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
                .orElseThrow(() -> new IllegalArgumentException("Id Nome Exercício não existe"));

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
                .orElseThrow(() -> new IllegalArgumentException("Id Grupo Muscular inválido"));

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
                .orElseThrow(() -> new InfoAlreadyExistsException("Id Nome Exercício não existe"));

    }
}
