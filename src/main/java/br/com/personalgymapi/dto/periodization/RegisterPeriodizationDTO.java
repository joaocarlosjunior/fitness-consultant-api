package br.com.personalgymapi.dto.periodization;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterPeriodizationDTO {
    @JsonProperty("name")
    @NotBlank(message = "Nome da periodização obrigatório")
    private String name;

    @JsonProperty("number_weeks")
    private Integer numberWeeks;

    @JsonProperty("id_user")
    @NotNull(message = "Usuário obrigatório")
    private Long user;
}
