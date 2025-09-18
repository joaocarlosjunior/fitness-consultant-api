package br.com.fitnessconsultant.dto.musuculegroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseMuscleGroupDTO {
    private Long idMuscleGroup;

    private String name;
}
