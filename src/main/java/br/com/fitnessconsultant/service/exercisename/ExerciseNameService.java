package br.com.fitnessconsultant.service.exercisename;

import br.com.fitnessconsultant.dto.exercisename.RecoveryExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.RegisterExerciseNameDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ExerciseNameService {
    void createExerciseName(RegisterExerciseNameDTO registerExerciseNameDTO);

    RecoveryExerciseNameDTO getExerciseNameById(Long id);

    RecoveryExerciseNameDTO updateExerciseNameById(Long id,
                                                   RegisterExerciseNameDTO registerExerciseNameDTO);

    void deleteExerciseNameById(@PathVariable Long id);

    List<RecoveryExerciseNameDTO> getAllExerciseName();
}
