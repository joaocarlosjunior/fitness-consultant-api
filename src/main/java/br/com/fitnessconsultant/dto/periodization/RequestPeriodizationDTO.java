package br.com.fitnessconsultant.dto.periodization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RequestPeriodizationDTO (
        @NotBlank(message = "Campo nome periodização é obrigatório")
        String name,
        @NotNull(message = "Campo número de semanas da periodização é obrigatório")
        @Positive
        Integer numberWeeks,
        @Positive
        @NotNull(message = "Id usuário é obrigatório")
        Long idUser
){}
