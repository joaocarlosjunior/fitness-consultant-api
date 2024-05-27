package br.com.personalgymapi.dto.periodization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecoveryPeriodizationDTO {
    private Long idPeriodization;

    private Long idUser;

    private String name;

    private Integer numberWeeks;

    private String startDate;
}
