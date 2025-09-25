package br.com.fitnessconsultant.common;

import br.com.fitnessconsultant.domain.entities.MuscleGroup;
import br.com.fitnessconsultant.dto.musuculegroup.RequestMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.ResponseMuscleGroupDTO;

import java.util.Arrays;
import java.util.List;

public class MusculeGroupConstants {
    public static final MuscleGroup MUSCLE_GROUP = new MuscleGroup(1L, "Nome grupo muscular");
    public static final RequestMuscleGroupDTO MUSCULE_GROUP_REQUEST = new RequestMuscleGroupDTO("Nome grupo muscular");
    public static final ResponseMuscleGroupDTO MUSCLE_GROUP_RESPONSE = new ResponseMuscleGroupDTO(1L, "Nome grupo muscular");
    public static final List<ResponseMuscleGroupDTO> MUSCLE_GROUP_LIST = Arrays.asList(
            new ResponseMuscleGroupDTO(1L, "Nome grupo muscular 1"),
            new ResponseMuscleGroupDTO(2L, "Nome grupo muscular 2"),
            new ResponseMuscleGroupDTO(3L, "Nome grupo muscular 3")
    );
}
