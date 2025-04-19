package br.com.fitnessconsultant.dto.periodization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestPeriodizationDTO (
        @NotBlank(message = "Campo nome periodização é obrigatório")
        String name,
        @NotNull(message = "Campo nome periodização é obrigatório")
        Integer numberWeeks,
        @NotNull(message = "Usuário é obrigatório")
        Long idUser
){}
