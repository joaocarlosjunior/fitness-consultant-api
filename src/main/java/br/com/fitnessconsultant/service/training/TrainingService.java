package br.com.fitnessconsultant.service.training;

import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;
import br.com.fitnessconsultant.dto.training.RequestTrainingDTO;

import java.util.List;

public interface TrainingService {
    ResponseTrainingDTO create(RequestTrainingDTO requestTrainingDTO);

    ResponseTrainingDTO update(Long id, RequestTrainingDTO requestTrainingDTO);

    void delete(Long id);

    ResponseTrainingDTO findById(Long id);

    List<ResponseTrainingDTO> getAllTrainingByIdPeriodization(Long id);
}
