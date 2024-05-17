package br.com.personalgymapi.dto.musuculegroup;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterMuscleGroupDTO {
    @NotBlank(message = "Campo Nome Grupo Muscular obrigatório")
    private String name;
}
