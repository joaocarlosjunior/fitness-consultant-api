package br.com.fitnessconsultant.service.musclegroup;

import br.com.fitnessconsultant.dto.musuculegroup.RequestMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.ResponseMuscleGroupDTO;

import java.util.List;

public interface MuscleGroupService {
    void create(RequestMuscleGroupDTO requestMuscleGroupDTO);

    ResponseMuscleGroupDTO findById(Long id);

    void delete(Long id);

    ResponseMuscleGroupDTO update(Long id, RequestMuscleGroupDTO requestMuscleGroupDTO);

    List<ResponseMuscleGroupDTO> list();
}
