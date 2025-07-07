package br.com.fitnessconsultant.dto.periodization;

import jakarta.validation.constraints.*;

public record RequestPeriodizationDTO (
        @NotBlank(message = "Campo nome periodização é obrigatório")
        @Size(max = 100, message = "Campo nome periodização tamanho máximo 100")
        String name,
        @NotNull(message = "Campo número de semanas da periodização é obrigatório")
        @PositiveOrZero
        Integer numberWeeks,
        @Positive
        @NotNull(message = "Id usuário é obrigatório")
        Long idUser
){}
