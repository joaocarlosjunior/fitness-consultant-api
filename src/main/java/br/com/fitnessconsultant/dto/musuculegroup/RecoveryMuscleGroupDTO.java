package br.com.fitnessconsultant.dto.musuculegroup;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecoveryMuscleGroupDTO {
    private Long idMuscleGroup;

    private String name;
}
