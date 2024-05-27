package br.com.personalgymapi.dto.periodization;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePeriodizationDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private Integer numberWeeks;
}
