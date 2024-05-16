package br.com.personalgymapi.mapper.user;

import br.com.personalgymapi.domain.entities.User;
import br.com.personalgymapi.dto.user.UpdateUserDTO;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    void updateDtoFromUser(UpdateUserDTO registerUserDTO, @MappingTarget User user);

}
