package br.com.fitnessconsultant.mappers;

import br.com.fitnessconsultant.domain.entities.MuscleGroup;
import br.com.fitnessconsultant.dto.musuculegroup.ResponseMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.RequestMuscleGroupDTO;
import org.springframework.stereotype.Component;

@Component
public class MuscleGroupMapper {

    public ResponseMuscleGroupDTO toDto(MuscleGroup muscleGroup){
        if(muscleGroup == null){
            return null;
        }
        return ResponseMuscleGroupDTO
                .builder()
                .idMuscleGroup(muscleGroup.getId())
                .name(muscleGroup.getName())
                .build();
    }

    public MuscleGroup toEntity(RequestMuscleGroupDTO dto){
        if(dto == null){
            return null;
        }
        return MuscleGroup
                .builder()
                .name(dto.name().trim())
                .build();
    }
}
