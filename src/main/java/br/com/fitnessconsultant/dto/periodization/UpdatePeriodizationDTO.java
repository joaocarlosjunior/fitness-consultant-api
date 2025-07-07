package br.com.fitnessconsultant.dto.periodization;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record UpdatePeriodizationDTO (
        @Size(max = 100, message = "Campo nome periodização tamanho máximo 100")
        String name,
        @PositiveOrZero
        Integer numberWeeks,
        @Positive
        Long idUser
){}
