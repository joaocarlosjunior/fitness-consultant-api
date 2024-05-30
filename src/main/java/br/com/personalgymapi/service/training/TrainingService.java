package br.com.personalgymapi.service.training;

import br.com.personalgymapi.dto.training.RecoveryTrainingDTO;
import br.com.personalgymapi.dto.training.RegisterTrainingDTO;
import br.com.personalgymapi.dto.training.UpdateTrainingDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TrainingService {
    RecoveryTrainingDTO createTraining(RegisterTrainingDTO registerTrainingDTO);

    RecoveryTrainingDTO updateTraining(Long id, RegisterTrainingDTO registerTrainingDTO);

    void deleteTraining(Long id);

    RecoveryTrainingDTO getTrainingById(Long id);

    List<RecoveryTrainingDTO> getAllTrainingByIdPeriodization(Long id);
}
