package br.com.fitnessconsultant.dto.user;

import br.com.fitnessconsultant.domain.enums.Role;
import br.com.fitnessconsultant.dto.periodization.RecoveryPeriodizationDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
}
