package br.com.personalgymapi.service.exercisename;

import br.com.personalgymapi.dto.exercisename.RecoveryExerciseNameDTO;
import br.com.personalgymapi.dto.exercisename.RegisterExerciseNameDTO;
import br.com.personalgymapi.dto.training.RegisterTrainingDTO;
import org.springframework.web.bind.annotation.PathVariable;

public interface ExerciseNameService {
    void createExerciseName(RegisterExerciseNameDTO registerExerciseNameDTO);

    RecoveryExerciseNameDTO getExerciseNameById(Long id);

    RecoveryExerciseNameDTO updateExerciseNameById(Long id,
                                                   RegisterExerciseNameDTO registerExerciseNameDTO);

    void deleteExerciseNameById(@PathVariable Long id);
}
