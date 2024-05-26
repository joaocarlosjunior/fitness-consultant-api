package br.com.personalgymapi.dto.periodization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class RecoveryPeriodizationDTO {
    private Long id;

    private Long idUser;

    private String name;

    private Integer numberWeeks;

    private String startDate;
}
