package br.com.fitnessconsultant.dto.user;

import br.com.fitnessconsultant.domain.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseUserDTO {
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
