package br.com.fitnessconsultant.dto.periodization;

import jakarta.validation.constraints.NotBlank;

public record UpdatePeriodizationDTO (
    @NotBlank(message = "Nome é obrigatório")
    String name,

    Integer numberWeeks
){}
