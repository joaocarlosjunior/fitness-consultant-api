package br.com.fitnessconsultant.dto.periodization;

import jakarta.validation.constraints.Positive;

public record UpdatePeriodizationDTO (
        String name,
        @Positive
        Integer numberWeeks
){}
