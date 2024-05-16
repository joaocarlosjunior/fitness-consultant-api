package br.com.personalgymapi.dto.user;

import br.com.personalgymapi.domain.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecoveryUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Role role;
}