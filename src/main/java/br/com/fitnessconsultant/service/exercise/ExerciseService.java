package br.com.fitnessconsultant.service.exercise;

import br.com.fitnessconsultant.dto.exercise.ResponseExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.RequestExerciseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExerciseService {
    ResponseEntity<ResponseExerciseDTO> create(RequestExerciseDTO requestExerciseDTO);

    ResponseEntity<ResponseExerciseDTO> update(Long id, RequestExerciseDTO requestExerciseDTO);

    void delete(Long id);

    ResponseEntity<List<ResponseExerciseDTO>> getAllExercisesByIdTraining(Long id);
}
