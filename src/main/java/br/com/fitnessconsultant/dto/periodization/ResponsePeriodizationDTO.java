package br.com.fitnessconsultant.dto.periodization;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponsePeriodizationDTO {
    private Long idPeriodization;

    private String name;

    private Integer numberWeeks;

    private String startDate;

    private String createdAt;

    private String updatedAt;
}
