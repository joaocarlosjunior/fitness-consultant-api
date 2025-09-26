package br.com.fitnessconsultant.service.exercisename.impl;

import br.com.fitnessconsultant.domain.entities.ExerciseName;
import br.com.fitnessconsultant.domain.entities.MuscleGroup;
import br.com.fitnessconsultant.domain.repository.ExerciseNameRepository;
import br.com.fitnessconsultant.domain.repository.MuscleGroupRepository;
import br.com.fitnessconsultant.dto.exercisename.RequestExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.RequestUpdateExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.ResponseExerciseNameDTO;
import br.com.fitnessconsultant.exception.ApiErrorException;
import br.com.fitnessconsultant.exception.InfoAlreadyExistsException;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.mappers.ExerciseNameMapper;
import br.com.fitnessconsultant.service.exercisename.ExerciseNameService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseNameServiceImpl implements ExerciseNameService {
    private final ExerciseNameRepository exerciseNameRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final ExerciseNameMapper exerciseNameMapper;

    public ExerciseNameServiceImpl(ExerciseNameRepository exerciseNameRepository,
                            MuscleGroupRepository muscleGroupRepository,
                            ExerciseNameMapper exerciseNameMapper) {
        this.exerciseNameRepository = exerciseNameRepository;
        this.muscleGroupRepository = muscleGroupRepository;
        this.exerciseNameMapper = exerciseNameMapper;
    }

    @Transactional
    public void create(@Valid @NotNull RequestExerciseNameDTO requestExerciseNameDTO) {
        boolean exerciseNameExists = exerciseNameRepository.
                existsByExerciseNameIgnoreCase(requestExerciseNameDTO.exerciseName());

        if (exerciseNameExists) {
            throw new InfoAlreadyExistsException("Nome Exercício já existente");
        }

        MuscleGroup muscleGroup = muscleGroupRepository
                .getReferenceById(requestExerciseNameDTO.idMuscleGroup());

        exerciseNameRepository.save(exerciseNameMapper.toEntity(requestExerciseNameDTO, muscleGroup));
    }

    @Transactional(readOnly = true)
    public ResponseExerciseNameDTO findById(@Positive @NotNull Long id) {
        ExerciseName exerciseName = exerciseNameRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Nome Exercicio não encontrado"));

        return exerciseNameMapper.toDto(exerciseName);
    }

    @Transactional
    public ResponseExerciseNameDTO update(@Positive @NotNull Long id, @Valid @NotNull RequestUpdateExerciseNameDTO requestUpdateExerciseNameDTO) {
        ExerciseName exerciseName = exerciseNameRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Nome Exercício não encontrado"));

        if (requestUpdateExerciseNameDTO.exerciseName() == null ||
                requestUpdateExerciseNameDTO.exerciseName().isBlank()) {
            throw new ApiErrorException("Campo nome exercicio inválido");
        }

        if (!requestUpdateExerciseNameDTO.exerciseName().equalsIgnoreCase(exerciseName.getExerciseName())) {
            boolean exerciseNameExists = exerciseNameRepository.
                    existsByExerciseNameIgnoreCase(requestUpdateExerciseNameDTO.exerciseName());

            if (exerciseNameExists) {
                throw new InfoAlreadyExistsException("Nome Exercício já existente");
            }

            exerciseName.setExerciseName(requestUpdateExerciseNameDTO.exerciseName());
        }

        if (requestUpdateExerciseNameDTO.idMuscleGroup() != null) {
            MuscleGroup muscleGroup = muscleGroupRepository
                    .findById(requestUpdateExerciseNameDTO.idMuscleGroup())
                    .orElseThrow(() -> new RecordNotFoundException("Grupo Muscular não encontrado"));

            exerciseName.setMuscleGroup(muscleGroup);
        }

        return exerciseNameMapper.toDto(exerciseNameRepository.save(exerciseName));
    }

    public void delete(@Positive @NotNull Long id) {
        exerciseNameRepository.delete(exerciseNameRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Nome Exercício não encontrado")));
    }

    @Transactional(readOnly = true)
    public List<ResponseExerciseNameDTO> list() {
        List<ExerciseName> exerciseNames = exerciseNameRepository.findAll();

        return exerciseNames
                .stream()
                .map(exerciseNameMapper::toDto)
                .collect(Collectors.toList());
    }
}
