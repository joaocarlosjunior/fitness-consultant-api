package br.com.fitnessconsultant.service.periodization;

import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PeriodizationService {
    ResponseEntity<ResponsePeriodizationDTO> create(RequestPeriodizationDTO registerPeriodization);

    ResponseEntity<ResponsePeriodizationDTO> update(Long id, UpdatePeriodizationDTO registerPeriodization);

    ResponseEntity<ResponsePeriodizationDTO> findById(Long id);

    void delete(Long id);

    ResponseEntity<List<ResponsePeriodizationDTO>> list();

    ResponseEntity<List<ResponsePeriodizationDTO>> getAllPeriodizationByUser(Long id);
}
