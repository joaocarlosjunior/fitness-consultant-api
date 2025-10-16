package br.com.fitnessconsultant.factories;

import br.com.fitnessconsultant.domain.entities.Training;
import br.com.fitnessconsultant.domain.enums.TrainingType;
import br.com.fitnessconsultant.dto.training.RequestTrainingDTO;
import br.com.fitnessconsultant.dto.training.RequestUpdateTrainingDTO;
import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static br.com.fitnessconsultant.factories.PeriodizationFactory.createPeriodization;

public class TrainingFactory {

    public static Training createTraining() {
        return new Training(
                1L,
                TrainingType.A,
                "Nome do treino",
                false,
                LocalDateTime.now(),
                LocalDateTime.now(),
                createPeriodization()
        );
    }

    public static RequestTrainingDTO createTrainingRequest() {
        return new RequestTrainingDTO(
                1L,
                1,
                "Nome do treino"
        );
    }

    public static ResponseTrainingDTO createTrainingResponse() {
        LocalDateTime now = LocalDateTime.now();
        return new ResponseTrainingDTO(
                1L,
                1L,
                TrainingType.A.name(),
                "Nome do treino",
                now.toString(),
                now.toString()
        );
    }

    public static RequestUpdateTrainingDTO createUpdateTrainingRequest() {
        return new RequestUpdateTrainingDTO(
                1L,
                1,
                "Nome do treino atualizado"
        );
    }

    public static List<ResponseTrainingDTO> createTrainingResponseList() {
        return IntStream.rangeClosed(1, 8)
                .mapToObj(i -> {
                    LocalDateTime now = LocalDateTime.now();
                    TrainingType type = TrainingType.values()[(i - 1) % TrainingType.values().length];
                    return new ResponseTrainingDTO(
                            (long) i,
                            1L,
                            type.name(),
                            "Nome do treino " + i,
                            now.toString(),
                            now.toString()
                    );
                })
                .collect(Collectors.toList());
    }

    public static List<Training> createTrainingList() {
        return IntStream.rangeClosed(1, 8)
                .mapToObj(i -> {
                    TrainingType type = TrainingType.values()[(i - 1) % TrainingType.values().length];
                    return new Training(
                            (long) i,
                            type,
                            "Nome do treino " + i,
                            false,
                            LocalDateTime.now(),
                            LocalDateTime.now(),
                            createPeriodization()
                    );
                })
                .collect(Collectors.toList());
    }
}
