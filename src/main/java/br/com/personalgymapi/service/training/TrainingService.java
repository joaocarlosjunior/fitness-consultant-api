package br.com.personalgymapi.service.training;

import br.com.personalgymapi.dto.training.RecoveryTrainingDTO;
import br.com.personalgymapi.dto.training.RegisterTrainingDTO;
import org.springframework.web.bind.annotation.PathVariable;

public interface TrainingService {
    RecoveryTrainingDTO createTraining(RegisterTrainingDTO registerTrainingDTO);

    RecoveryTrainingDTO updateTraining(Long id, RegisterTrainingDTO registerTrainingDTO);

    void deleteTraining(Long id);

    RecoveryTrainingDTO getTrainingById(Long id);
}
