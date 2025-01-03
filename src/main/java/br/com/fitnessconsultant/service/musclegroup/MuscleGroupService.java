package br.com.fitnessconsultant.service.musclegroup;

import br.com.fitnessconsultant.dto.musuculegroup.RequestMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.ResponseMuscleGroupDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MuscleGroupService {
    void create(RequestMuscleGroupDTO requestMuscleGroupDTO);

    ResponseEntity<ResponseMuscleGroupDTO> findById(Long id);

    void delete(Long id);

    ResponseEntity<ResponseMuscleGroupDTO> update(Long id, RequestMuscleGroupDTO requestMuscleGroupDTO);

    ResponseEntity<List<ResponseMuscleGroupDTO>> list();
}
