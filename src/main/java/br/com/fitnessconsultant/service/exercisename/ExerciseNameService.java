package br.com.fitnessconsultant.service.exercisename;

import br.com.fitnessconsultant.dto.exercisename.ResponseExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.RequestExerciseNameDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ExerciseNameService {
    void create(RequestExerciseNameDTO requestExerciseNameDTO);

    ResponseExerciseNameDTO findById(Long id);

    ResponseExerciseNameDTO update(Long id,
                                   RequestExerciseNameDTO requestExerciseNameDTO);

    void delete(@PathVariable Long id);

    List<ResponseExerciseNameDTO> list();
}
