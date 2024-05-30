package br.com.personalgymapi.service.periodization;

import br.com.personalgymapi.dto.periodization.RecoveryPeriodizationDTO;
import br.com.personalgymapi.dto.periodization.RegisterPeriodizationDTO;
import br.com.personalgymapi.dto.periodization.UpdatePeriodizationDTO;

import java.util.List;

public interface PeriodizationService {
    RecoveryPeriodizationDTO createPeriodization(RegisterPeriodizationDTO registerPeriodization);

    RecoveryPeriodizationDTO updatePeriodization(Long id, UpdatePeriodizationDTO registerPeriodization);

    RecoveryPeriodizationDTO getPeriodizationById(Long id);

    void deletePeriodization(Long id);

    List<RecoveryPeriodizationDTO> getAllPeriodization();

    List<RecoveryPeriodizationDTO> getAllPeriodizationByUser( Long id);
}
