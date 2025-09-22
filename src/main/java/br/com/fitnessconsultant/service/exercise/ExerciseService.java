package br.com.fitnessconsultant.service.exercise;

import br.com.fitnessconsultant.dto.exercise.RequestExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.ResponseExerciseDTO;

import java.util.List;

public interface ExerciseService {
    ResponseExerciseDTO create(RequestExerciseDTO requestExerciseDTO);

    ResponseExerciseDTO update(Long id, RequestExerciseDTO requestExerciseDTO);

    void delete(Long id);

    List<ResponseExerciseDTO> getAllExercisesByIdTraining(Long id);
}
