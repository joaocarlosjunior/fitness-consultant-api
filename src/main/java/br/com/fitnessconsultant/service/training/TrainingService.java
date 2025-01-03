package br.com.fitnessconsultant.service.training;

import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;
import br.com.fitnessconsultant.dto.training.RequestTrainingDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TrainingService {
    ResponseEntity<ResponseTrainingDTO> create(RequestTrainingDTO requestTrainingDTO);

    ResponseEntity<ResponseTrainingDTO> update(Long id, RequestTrainingDTO requestTrainingDTO);

    void delete(Long id);

    ResponseEntity<ResponseTrainingDTO> findById(Long id);

    ResponseEntity<List<ResponseTrainingDTO>> getAllTrainingByIdPeriodization(Long id);
}
