package br.com.fitnessconsultant.dto.musuculegroup;

import jakarta.validation.constraints.NotBlank;

public record RequestMuscleGroupDTO (
        @NotBlank(message = "Campo nome grupo muscular obrigatório")
        String name
){}
