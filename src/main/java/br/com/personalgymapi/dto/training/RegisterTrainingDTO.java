package br.com.personalgymapi.dto.training;

import br.com.personalgymapi.dto.musuculegroup.InfoMuscleGroupDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterTrainingDTO {
    @JsonProperty("id_user")
    @NotNull(message = "Campo id user não pode ser nulo")
    private Long idUser;

    @JsonProperty("training_type")
    @NotNull(message = "Campo training_type não pode ser nulo")
    private Integer trainingType;

    @JsonProperty("muscle_groups")
    @NotNull(message = "Campo Grupo Muscular obrigatório")
    private Set<InfoMuscleGroupDTO> muscleGroups;
}
