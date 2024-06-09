package br.com.fitnessconsultant.service.musclegroup.impl;

import br.com.fitnessconsultant.domain.entities.MuscleGroup;
import br.com.fitnessconsultant.domain.repository.MuscleGroupRepository;
import br.com.fitnessconsultant.dto.musuculegroup.RecoveryMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.RegisterMuscleGroupDTO;
import br.com.fitnessconsultant.exception.InfoAlreadyExistsException;
import br.com.fitnessconsultant.service.musclegroup.MuscleGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MuscleGroupImpl implements MuscleGroupService {

    private final MuscleGroupRepository muscleGroupRepository;

    @Transactional
    public void addMuscleGroup(RegisterMuscleGroupDTO registerMuscleGroupDTO) {

        if (muscleGroupRepository.existsByNameIgnoreCase(registerMuscleGroupDTO.getName().trim())) {
            throw new InfoAlreadyExistsException("Grupo Muscular já cadastrado");
        }

        MuscleGroup newMuscleGroup = MuscleGroup
                .builder()
                .name(registerMuscleGroupDTO.getName().trim())
                .build();

        muscleGroupRepository.save(newMuscleGroup);
    }

    @Transactional(readOnly = true)
    public RecoveryMuscleGroupDTO getMuscleGroupById(Long id) {
        return muscleGroupRepository
                .findById(id)
                .map(muscleGroup -> RecoveryMuscleGroupDTO
                        .builder()
                        .idMuscleGroup(muscleGroup.getId())
                        .name(muscleGroup.getName())
                        .build()
                )
                .orElseThrow(() -> new IllegalArgumentException("Id Grupo Muscular inválido ou inexistente"));
    }

    @Transactional
    public void deletedById(Long id) {
        muscleGroupRepository
                .findById(id)
                .map(muscleGroup -> {
                    muscleGroupRepository.delete(muscleGroup);
                    return Void.class;
                })
                .orElseThrow(() -> new IllegalArgumentException("Id Grupo Muscular inválido ou inexistente"));
    }

    @Transactional
    public RecoveryMuscleGroupDTO update(Long id, RegisterMuscleGroupDTO registerMuscleGroupDTO) {
        if (muscleGroupRepository.existsByNameIgnoreCase(registerMuscleGroupDTO.getName())) {
            throw new InfoAlreadyExistsException("Grupo Muscular já cadastrado");
        }

        MuscleGroup muscleGroup = muscleGroupRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id grupo muscular inválido ou inexistente"));

        muscleGroup.setName(registerMuscleGroupDTO.getName());

        MuscleGroup updateMuscleGroup = muscleGroupRepository.save(muscleGroup);

        return RecoveryMuscleGroupDTO.builder()
                .idMuscleGroup(updateMuscleGroup.getId())
                .name(updateMuscleGroup.getName())
                .build();
    }

    @Transactional(readOnly = true)
    public List<RecoveryMuscleGroupDTO> getAllMuscleGroups() {
        return muscleGroupRepository.findAll()
                .stream().map(muscleGroup -> RecoveryMuscleGroupDTO
                        .builder()
                        .idMuscleGroup(muscleGroup.getId())
                        .name(muscleGroup.getName())
                        .build()
                ).collect(Collectors.toList());
    }
}
