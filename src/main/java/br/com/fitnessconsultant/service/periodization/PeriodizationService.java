package br.com.fitnessconsultant.service.periodization;

import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;

import java.util.List;

public interface PeriodizationService {
    ResponsePeriodizationDTO create(RequestPeriodizationDTO registerPeriodization);

    ResponsePeriodizationDTO update(Long id, UpdatePeriodizationDTO registerPeriodization);

    ResponsePeriodizationDTO findById(Long id);

    void delete(Long id);

    List<ResponsePeriodizationDTO> list();

    List<ResponsePeriodizationDTO> getAllPeriodizationByUser(Long id);
}
