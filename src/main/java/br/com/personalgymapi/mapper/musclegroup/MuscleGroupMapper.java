package br.com.personalgymapi.mapper.musclegroup;

import br.com.personalgymapi.domain.entities.MuscleGroup;
import br.com.personalgymapi.dto.musuculegroup.RegisterMuscleGroupDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MuscleGroupMapper {
    void toEntity(RegisterMuscleGroupDTO dto, @MappingTarget MuscleGroup entity);
}
