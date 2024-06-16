package br.com.fitnessconsultant.dto.periodization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestPeriodizationDTO (
    @NotBlank(message = "Nome da periodização obrigatório")
    String name,

    Integer numberWeeks,

    @NotNull(message = "Usuário obrigatório")
    Long idUser
){}
