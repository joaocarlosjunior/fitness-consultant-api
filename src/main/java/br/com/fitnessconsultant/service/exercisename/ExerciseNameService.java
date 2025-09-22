package br.com.fitnessconsultant.service.exercisename;

import br.com.fitnessconsultant.dto.exercisename.RequestExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.RequestUpdateExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.ResponseExerciseNameDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ExerciseNameService {
    void create(RequestExerciseNameDTO requestExerciseNameDTO);

    ResponseExerciseNameDTO findById(Long id);

    ResponseExerciseNameDTO update(Long id, RequestUpdateExerciseNameDTO requestUpdateExerciseNameDTO);

    void delete(@PathVariable Long id);

    List<ResponseExerciseNameDTO> list();
}
