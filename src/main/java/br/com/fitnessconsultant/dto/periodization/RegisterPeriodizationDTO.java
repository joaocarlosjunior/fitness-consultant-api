package br.com.fitnessconsultant.dto.periodization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterPeriodizationDTO {
    @NotBlank(message = "Nome da periodização obrigatório")
    private String name;

    private Integer numberWeeks;

    @NotNull(message = "Usuário obrigatório")
    private Long idUser;
}
