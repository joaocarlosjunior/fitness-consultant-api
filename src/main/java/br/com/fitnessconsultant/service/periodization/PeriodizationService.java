package br.com.fitnessconsultant.service.periodization;

import br.com.fitnessconsultant.dto.periodization.RecoveryPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.RegisterPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;

import java.util.List;

public interface PeriodizationService {
    RecoveryPeriodizationDTO createPeriodization(RegisterPeriodizationDTO registerPeriodization);

    RecoveryPeriodizationDTO updatePeriodization(Long id, UpdatePeriodizationDTO registerPeriodization);

    RecoveryPeriodizationDTO getPeriodizationById(Long id);

    void deletePeriodization(Long id);

    List<RecoveryPeriodizationDTO> getAllPeriodization();

    List<RecoveryPeriodizationDTO> getAllPeriodizationByUser( Long id);
}
