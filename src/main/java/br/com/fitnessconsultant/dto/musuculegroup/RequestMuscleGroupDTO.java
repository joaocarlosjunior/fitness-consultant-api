package br.com.fitnessconsultant.dto.musuculegroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RequestMuscleGroupDTO (
    @NotBlank(message = "{field.groupmuscle.name}")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ]+$", message = "O campo Nome Grupo Muscular deve conter apenas letras")
    String name
){}
