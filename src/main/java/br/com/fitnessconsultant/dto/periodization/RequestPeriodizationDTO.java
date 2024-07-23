package br.com.fitnessconsultant.dto.periodization;

public record RequestPeriodizationDTO (
    String name,
    Integer numberWeeks,
    Long idUser
){}
