package br.com.fitnessconsultant.service.training;

import br.com.fitnessconsultant.dto.training.RecoveryTrainingDTO;
import br.com.fitnessconsultant.dto.training.RegisterTrainingDTO;

import java.util.List;

public interface TrainingService {
    RecoveryTrainingDTO createTraining(RegisterTrainingDTO registerTrainingDTO);

    RecoveryTrainingDTO updateTraining(Long id, RegisterTrainingDTO registerTrainingDTO);

    void deleteTraining(Long id);

    RecoveryTrainingDTO getTrainingById(Long id);

    List<RecoveryTrainingDTO> getAllTrainingByIdPeriodization(Long id);
}
