package br.com.personalgymapi.dto.user;

import br.com.personalgymapi.domain.enums.Role;
import br.com.personalgymapi.dto.periodization.RecoveryPeriodizationDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecoveryUserDTO {
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("role")
    private Role role;

    @JsonProperty("created_at")
    private String created_at;

    @JsonProperty("updated_at")
    private String updated_at;

    @JsonProperty("is_active")
    private boolean isActive;

    @JsonProperty("periodizations")
    private List<RecoveryPeriodizationDTO> periodizations;
}
