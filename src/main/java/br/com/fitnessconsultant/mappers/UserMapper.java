package br.com.fitnessconsultant.mappers;

import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.enums.Role;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.utils.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public ResponseUserDTO toDto(User user) {
        if(user == null){
            return null;
        }
        return ResponseUserDTO
                .builder()
                .idUser(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .isActive(user.isActive())
                .createdAt(DateUtils.formatDate(user.getCreatedAt()))
                .updatedAt(DateUtils.checkUpdateDate(user.getUpdatedAt()))
                .build();
    }

    public User toEntity(RequestUserDTO dto) {
        if(dto == null){
            return null;
        }
        return User
                .builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email().toLowerCase())
                .password(dto.password())
                .phone(dto.phone())
                .isActive(true)
                .role(Role.fromValue(dto.role()))
                .build();
    }
}
