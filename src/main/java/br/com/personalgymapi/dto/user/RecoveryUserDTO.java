package br.com.personalgymapi.dto.user;

import br.com.personalgymapi.domain.enums.Role;
import br.com.personalgymapi.dto.periodization.RecoveryPeriodizationDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RecoveryUserDTO {
    private Long idUser;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Role role;

    private String createdAt;

    private String updatedAt;

    private boolean isActive;

    private Set<RecoveryPeriodizationDTO> periodizations;
}
