package br.com.personalgymapi.mapper.user;

import br.com.personalgymapi.domain.entities.User;
import br.com.personalgymapi.dto.user.UpdateUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper{
    void toEntity(UpdateUserDTO dto, @MappingTarget User entity);
}
