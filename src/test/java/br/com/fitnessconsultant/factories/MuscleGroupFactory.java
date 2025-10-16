package br.com.fitnessconsultant.factories;

import br.com.fitnessconsultant.domain.entities.MuscleGroup;
import br.com.fitnessconsultant.dto.musuculegroup.RequestMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.ResponseMuscleGroupDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MuscleGroupFactory {

    public static MuscleGroup createMuscleGroup() {
        return new MuscleGroup(1L, "Nome grupo muscular");
    }

    public static RequestMuscleGroupDTO createMuscleGroupRequest() {
        return new RequestMuscleGroupDTO("Nome grupo muscular");
    }

    public static ResponseMuscleGroupDTO createMuscleGroupResponse() {
        return new ResponseMuscleGroupDTO(1L, "Nome grupo muscular");
    }

    public static List<ResponseMuscleGroupDTO> createMuscleGroupList() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> new ResponseMuscleGroupDTO(
                        (long) i,
                        "Nome grupo muscular " + i
                ))
                .collect(Collectors.toList());
    }
}
