package br.com.personalgymapi.service.musclegroup.impl;

import br.com.personalgymapi.domain.entities.MuscleGroup;
import br.com.personalgymapi.domain.repository.MuscleGroupRepository;
import br.com.personalgymapi.dto.musuculegroup.RegisterMuscleGroupDTO;
import br.com.personalgymapi.dto.musuculegroup.RecoveryMuscleGroupDTO;
import br.com.personalgymapi.exception.InfoAlreadyExistsException;
import br.com.personalgymapi.mapper.musclegroup.MuscleGroupMapper;
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
    private final MuscleGroupMapper muscleGroupMapper;

    @Transactional
    public void addMuscleGroup(RegisterMuscleGroupDTO registerMuscleGroupDTO) {
        checkMuscleGroupAlreadyExists(registerMuscleGroupDTO.getName());

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
                        .name(muscleGroup.getName())
                        .build()
                )
                .orElseThrow(() -> new IllegalArgumentException("Id Grupo Muscular inválido"));
    }

    @Transactional
    public void deletedById(Long id) {
        muscleGroupRepository
                .findById(id)
                .map(muscleGroup -> {
                    muscleGroupRepository.delete(muscleGroup);
                    return Void.class;
                })
                .orElseThrow(() -> new IllegalArgumentException("Id Grupo Muscular não encontrado"));
    }

    @Transactional
    public RecoveryMuscleGroupDTO update(Long id, RegisterMuscleGroupDTO registerMuscleGroupDTO) {
        checkMuscleGroupAlreadyExists(registerMuscleGroupDTO.getName());

        MuscleGroup muscleGroup = muscleGroupRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id grupo muscular inválido"));

        muscleGroupMapper.toEntity(registerMuscleGroupDTO, muscleGroup);

        MuscleGroup updateMuscleGroup = muscleGroupRepository.save(muscleGroup);

        return RecoveryMuscleGroupDTO.builder()
                .name(updateMuscleGroup.getName())
                .build();
    }

    @Transactional(readOnly = true)
    public List<RecoveryMuscleGroupDTO> getAllMuscleGroups() {
        return muscleGroupRepository.findAll()
                .stream().map(muscleGroup -> RecoveryMuscleGroupDTO
                        .builder()
                        .name(muscleGroup.getName())
                        .build()
                ).collect(Collectors.toList());
    }

    private void checkMuscleGroupAlreadyExists(String nameMuscle) {
        nameMuscle = nameMuscle.trim();
        if (muscleGroupRepository.findByName(nameMuscle).isPresent()) {
            throw new InfoAlreadyExistsException("Grupo Muscular já existe");
        }
    }
}
