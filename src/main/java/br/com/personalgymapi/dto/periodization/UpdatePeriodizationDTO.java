package br.com.personalgymapi.dto.periodization;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdatePeriodizationDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private Integer numberWeeks;
}
