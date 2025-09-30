package br.com.fitnessconsultant.common;

import br.com.fitnessconsultant.domain.entities.Periodization;
import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static br.com.fitnessconsultant.common.UserConstants.USER;

public class PeriodizationConstants {
    public static final Periodization PERIODIZATION = new Periodization(1L, "Nome Periodizacao", 4, LocalDateTime.now(),LocalDateTime.now(),LocalDateTime.now(), USER);
    public static final RequestPeriodizationDTO PERIODIZATION_REQUEST = new RequestPeriodizationDTO("Nome Periodizacao", 4, 1L);
    public static final ResponsePeriodizationDTO PERIODIZATION_RESPONSE = new ResponsePeriodizationDTO(1L, "Nome Periodizacao", 4, LocalDateTime.now().toString(), LocalDateTime.now().toString(), LocalDateTime.now().toString());
    public static final List<ResponsePeriodizationDTO> PERIODIZATION_LIST = Arrays.asList(
            new ResponsePeriodizationDTO(1L, "Nome Periodizacao 1", 4, LocalDateTime.now().toString(), LocalDateTime.now().toString(), LocalDateTime.now().toString()),
            new ResponsePeriodizationDTO(2L, "Nome Periodizacao 2", 4, LocalDateTime.now().toString(), LocalDateTime.now().toString(), LocalDateTime.now().toString()),
            new ResponsePeriodizationDTO(3L, "Nome Periodizacao 3", 4, LocalDateTime.now().toString(), LocalDateTime.now().toString(), LocalDateTime.now().toString()),
            new ResponsePeriodizationDTO(4L, "Nome Periodizacao 4", 4, LocalDateTime.now().toString(), LocalDateTime.now().toString(), LocalDateTime.now().toString())
    );
}
