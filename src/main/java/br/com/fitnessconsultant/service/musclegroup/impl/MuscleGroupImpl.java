package br.com.fitnessconsultant.service.musclegroup.impl;

import br.com.fitnessconsultant.domain.entities.MuscleGroup;
import br.com.fitnessconsultant.domain.repository.MuscleGroupRepository;
import br.com.fitnessconsultant.dto.musuculegroup.ResponseMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.RequestMuscleGroupDTO;
import br.com.fitnessconsultant.exception.InfoAlreadyExistsException;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.mappers.MuscleGroupMapper;
import br.com.fitnessconsultant.service.musclegroup.MuscleGroupService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MuscleGroupImpl implements MuscleGroupService {

    private final MuscleGroupRepository muscleGroupRepository;
    private final MuscleGroupMapper muscleGroupMapper;

    public MuscleGroupImpl(MuscleGroupRepository muscleGroupRepository,
                           MuscleGroupMapper muscleGroupMapper) {
        this.muscleGroupRepository = muscleGroupRepository;
        this.muscleGroupMapper = muscleGroupMapper;
    }

    @Transactional
    public void create(@Valid @NotNull RequestMuscleGroupDTO requestMuscleGroupDTO) {

        if (muscleGroupRepository.existsByNameIgnoreCase(requestMuscleGroupDTO.getName().trim())) {
            throw new InfoAlreadyExistsException("Grupo Muscular já cadastrado");
        }

        muscleGroupRepository.save(muscleGroupMapper.toEntity(requestMuscleGroupDTO));
    }

    @Transactional(readOnly = true)
    public ResponseMuscleGroupDTO findById(@NotNull @Positive Long id) {
        return muscleGroupRepository
                .findById(id)
                .map(muscleGroupMapper::toDto
                )
                .orElseThrow(() -> new RecordNotFoundException("Grupo Muscular não encontrado"));
    }

    @Transactional
    public void delete(@NotNull @Positive Long id) {
        muscleGroupRepository.delete(muscleGroupRepository
                        .findById(id)
                        .orElseThrow(() -> new RecordNotFoundException("Grupo Muscular não encontrado")));
    }

    @Transactional
    public ResponseMuscleGroupDTO update(@NotNull @Positive Long id, @Valid @NotNull RequestMuscleGroupDTO requestMuscleGroupDTO) {
        if (muscleGroupRepository.existsByNameIgnoreCase(requestMuscleGroupDTO.getName())) {
            throw new InfoAlreadyExistsException("Grupo Muscular já cadastrado");
        }

        MuscleGroup muscleGroup = muscleGroupRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Grupo Muscular não encontrado"));

        muscleGroup.setName(requestMuscleGroupDTO.getName());

        return muscleGroupMapper.toDto(muscleGroupRepository.save(muscleGroup));
    }

    @Transactional(readOnly = true)
    public List<ResponseMuscleGroupDTO> list() {
        return muscleGroupRepository.findAll()
                .stream().map(muscleGroupMapper::toDto)
                .collect(Collectors.toList());
    }
}
