package br.com.personalgymapi.service.musclegroup;

import br.com.personalgymapi.dto.musuculegroup.RegisterMuscleGroupDTO;
import br.com.personalgymapi.dto.musuculegroup.RecoveryMuscleGroupDTO;

import java.util.List;

public interface MuscleGroupService {
    void addMuscleGroup(RegisterMuscleGroupDTO registerMuscleGroupDTO);

    RecoveryMuscleGroupDTO getMuscleGroupById(Long id);

    List<RecoveryMuscleGroupDTO> getAllMuscleGroups();
}
