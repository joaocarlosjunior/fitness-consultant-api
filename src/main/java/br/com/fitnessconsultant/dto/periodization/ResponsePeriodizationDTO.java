package br.com.fitnessconsultant.dto.periodization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponsePeriodizationDTO {
    private Long idPeriodization;

    private String name;

    private Integer numberWeeks;

    private String startDate;

    private String createdAt;

    private String updatedAt;
}
