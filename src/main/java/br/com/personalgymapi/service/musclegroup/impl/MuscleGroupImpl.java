package br.com.personalgymapi.service.musclegroup.impl;

import br.com.personalgymapi.domain.entities.MuscleGroup;
import br.com.personalgymapi.domain.repository.MuscleGroupRepository;
import br.com.personalgymapi.dto.musuculegroup.RegisterMuscleGroupDTO;
import br.com.personalgymapi.dto.musuculegroup.RecoveryMuscleGroupDTO;
import br.com.personalgymapi.service.musclegroup.MuscleGroupService;
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
        MuscleGroup newMuscleGroup = MuscleGroup
                .builder()
                .name(registerMuscleGroupDTO.getName())
                .build();

        muscleGroupRepository.save(newMuscleGroup);
    }

    public RecoveryMuscleGroupDTO getMuscleGroupById(Long id) {
       return muscleGroupRepository
                .findById(id)
                .map(muscleGroup -> RecoveryMuscleGroupDTO
                        .builder()
                        .name(muscleGroup.getName())
                        .build()
                )
                .orElseThrow(() -> new IllegalArgumentException("Grupo Muscular inválido"));
    }

    public List<RecoveryMuscleGroupDTO> getAllMuscleGroups() {
        return muscleGroupRepository.findAll()
                .stream().map(muscleGroup -> RecoveryMuscleGroupDTO
                        .builder()
                        .name(muscleGroup.getName())
                        .build()
                ).collect(Collectors.toList());
    }
}
