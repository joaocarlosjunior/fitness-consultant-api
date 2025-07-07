package br.com.fitnessconsultant.dto.musuculegroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestMuscleGroupDTO(
        @NotBlank(message = "Campo nome grupo muscular obrigatório")
        @Size(max = 100, message = "Campo nome grupo muscular tem tamanho máximo 100")
        String name
) {
}
