package br.com.personalgymapi.dto.periodization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterPeriodizationDTO {
    @NotBlank(message = "Nome da periodização obrigatório")
    private String name;

    private Integer numberWeeks;

    @NotNull(message = "Usuário obrigatório")
    private Long user;
}
