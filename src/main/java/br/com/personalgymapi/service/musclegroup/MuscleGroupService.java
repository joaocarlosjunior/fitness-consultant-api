package br.com.personalgymapi.service.musclegroup;

import br.com.personalgymapi.dto.musuculegroup.RegisterMuscleGroupDTO;
import br.com.personalgymapi.dto.musuculegroup.RecoveryMuscleGroupDTO;

import java.util.List;

public interface MuscleGroupService {
    void addMuscleGroup(RegisterMuscleGroupDTO registerMuscleGroupDTO);

    RecoveryMuscleGroupDTO getMuscleGroupById(Long id);

    void deletedById(Long id);

    RecoveryMuscleGroupDTO update(Long id, RegisterMuscleGroupDTO registerMuscleGroupDTO);

    List<RecoveryMuscleGroupDTO> getAllMuscleGroups();
}
