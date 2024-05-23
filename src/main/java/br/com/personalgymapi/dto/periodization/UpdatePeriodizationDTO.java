package br.com.personalgymapi.dto.periodization;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdatePeriodizationDTO {
    @JsonProperty("name")
    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @JsonProperty("number_weeks")
    private Integer numberWeeks;
}
