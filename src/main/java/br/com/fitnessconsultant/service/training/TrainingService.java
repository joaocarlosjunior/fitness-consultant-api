package br.com.fitnessconsultant.service.training;

import br.com.fitnessconsultant.dto.training.RequestTrainingDTO;
import br.com.fitnessconsultant.dto.training.RequestUpdateTrainingDTO;
import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;

import java.util.List;

public interface TrainingService {
    ResponseTrainingDTO create(RequestTrainingDTO requestTrainingDTO);

    ResponseTrainingDTO update(Long id, RequestUpdateTrainingDTO requestUpdateTrainingDTO);

    void delete(Long id);

    ResponseTrainingDTO findById(Long id);

    List<ResponseTrainingDTO> getAllTrainingByIdPeriodization(Long id);
}
