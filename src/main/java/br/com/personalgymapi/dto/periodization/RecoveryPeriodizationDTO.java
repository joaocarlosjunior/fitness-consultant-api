package br.com.personalgymapi.dto.periodization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class RecoveryPeriodizationDTO {
    @JsonProperty("id_periodization")
    private Long id;

    @JsonProperty("id_user")
    private Long idUser;

    @JsonProperty("name")
    private String name;

    @JsonProperty("number_weeks")
    private Integer numberWeeks;

    @JsonProperty("start_date")
    private String startDate;
}
