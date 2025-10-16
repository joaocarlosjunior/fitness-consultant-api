package br.com.fitnessconsultant.factories;

import br.com.fitnessconsultant.domain.entities.Periodization;
import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static br.com.fitnessconsultant.factories.UserFactory.createUser;

public class PeriodizationFactory {

    public static Periodization createPeriodization() {
        return new Periodization(
                1L,
                "Nome Periodizacao",
                4,
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                createUser()
        );
    }

    public static RequestPeriodizationDTO createPeriodizationRequest() {
        return new RequestPeriodizationDTO(
                "Nome Periodizacao",
                4,
                1L
        );
    }

    public static ResponsePeriodizationDTO createPeriodizationResponse() {
        LocalDateTime now = LocalDateTime.now();
        return new ResponsePeriodizationDTO(
                1L,
                "Nome Periodizacao",
                4,
                now.toString(),
                now.toString(),
                now.toString()
        );
    }

    public static UpdatePeriodizationDTO createUpdatePeriodization() {
        return new UpdatePeriodizationDTO(
                "Nome Periodizacao Atualizada",
                4,
                1L
        );
    }

    public static List<ResponsePeriodizationDTO> createPeriodizationListResponse() {
        return IntStream.rangeClosed(1, 4)
                .mapToObj(i -> {
                    LocalDateTime now = LocalDateTime.now();
                    return new ResponsePeriodizationDTO(
                            (long) i,
                            "Nome Periodizacao " + i,
                            4,
                            now.toString(),
                            now.toString(),
                            now.toString()
                    );
                })
                .collect(Collectors.toList());
    }

    public static List<Periodization> createPeriodizationList() {
        return IntStream.rangeClosed(1, 4)
                .mapToObj(i -> new Periodization(
                        (long) i,
                        "Nome Periodizacao " + i,
                        4,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        createUser()
                ))
                .collect(Collectors.toList());
    }
}

