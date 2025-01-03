package br.com.fitnessconsultant.service.exercisename;

import br.com.fitnessconsultant.dto.exercisename.ResponseExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.RequestExerciseNameDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ExerciseNameService {
    void create(RequestExerciseNameDTO requestExerciseNameDTO);

    ResponseEntity<ResponseExerciseNameDTO> findById(Long id);

    ResponseEntity<ResponseExerciseNameDTO> update(Long id,
                                   RequestExerciseNameDTO requestExerciseNameDTO);

    void delete(@PathVariable Long id);

    ResponseEntity<List<ResponseExerciseNameDTO>> list();
}
