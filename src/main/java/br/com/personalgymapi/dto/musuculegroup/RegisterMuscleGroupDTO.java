package br.com.personalgymapi.dto.musuculegroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterMuscleGroupDTO {
    @NotBlank(message = "{field.groupmuscle.name}")
    @Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ' ]+$", message = "O campo Nome Grupo Muscular deve conter apenas letras")
    private String name;
}
