package br.com.fitnessconsultant.common;

import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;

import java.util.Arrays;
import java.util.List;

public class PeriodizationConstants {
    public static final RequestPeriodizationDTO PERIODIZATION = new RequestPeriodizationDTO("Nome Periodizacao", 4, 1L);
    public static final ResponsePeriodizationDTO PERIODIZATION_RESPONSE = new ResponsePeriodizationDTO(1L, "Nome Periodizacao", 4, "2025-09-16T15:42:30", "2025-09-16T15:42:30", "2025-09-16T15:42:30");
    public static final List<ResponsePeriodizationDTO> PERIODIZATION_LIST = Arrays.asList(
            new ResponsePeriodizationDTO(1L, "Nome Periodizacao 1", 4, "2025-09-16T15:42:30", "2025-09-16T15:42:30", "2025-09-16T15:42:30"),
            new ResponsePeriodizationDTO(2L, "Nome Periodizacao 2", 4, "2025-09-16T15:42:30", "2025-09-16T15:42:30", "2025-09-16T15:42:30"),
            new ResponsePeriodizationDTO(3L, "Nome Periodizacao 3", 4, "2025-09-16T15:42:30", "2025-09-16T15:42:30", "2025-09-16T15:42:30"),
            new ResponsePeriodizationDTO(4L, "Nome Periodizacao 4", 4, "2025-09-16T15:42:30", "2025-09-16T15:42:30", "2025-09-16T15:42:30")
    );
}
