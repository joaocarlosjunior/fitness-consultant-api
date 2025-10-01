package br.com.fitnessconsultant.common;

import br.com.fitnessconsultant.domain.entities.Training;
import br.com.fitnessconsultant.domain.enums.TrainingType;
import br.com.fitnessconsultant.dto.training.RequestTrainingDTO;
import br.com.fitnessconsultant.dto.training.RequestUpdateTrainingDTO;
import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static br.com.fitnessconsultant.common.PeriodizationConstants.PERIODIZATION;

public class TrainingConstants {
    public static final Training TRAINING = new Training(1L, TrainingType.A, "Nome do treino", false, LocalDateTime.now(), LocalDateTime.now(), PERIODIZATION);
    public static final RequestTrainingDTO TRAINING_REQUEST = new RequestTrainingDTO(1L, 1, "Nome do treino");
    public static final ResponseTrainingDTO RESPONSE_TRAINING = new ResponseTrainingDTO(1L, 1L,"A", "Nome do treino", "2025-09-16T15:42:30", "2025-09-16T15:42:30");
    public static final List<ResponseTrainingDTO> RESPONSE_TRAININGS = Arrays.asList(
            new ResponseTrainingDTO(1L, PERIODIZATION.getId(),"A", "Nome do treino", LocalDateTime.now().toString(), LocalDateTime.now().toString()),
            new ResponseTrainingDTO(2L, PERIODIZATION.getId(),"B", "Nome do treino", LocalDateTime.now().toString(), LocalDateTime.now().toString()),
            new ResponseTrainingDTO(3L, PERIODIZATION.getId(),"C", "Nome do treino", LocalDateTime.now().toString(), LocalDateTime.now().toString()),
            new ResponseTrainingDTO(4L, PERIODIZATION.getId(),"D", "Nome do treino", LocalDateTime.now().toString(), LocalDateTime.now().toString()),
            new ResponseTrainingDTO(5L, PERIODIZATION.getId(),"A", "Nome do treino", LocalDateTime.now().toString(), LocalDateTime.now().toString()),
            new ResponseTrainingDTO(6L, PERIODIZATION.getId(),"B", "Nome do treino", LocalDateTime.now().toString(), LocalDateTime.now().toString()),
            new ResponseTrainingDTO(7L, PERIODIZATION.getId(),"C", "Nome do treino", LocalDateTime.now().toString(), LocalDateTime.now().toString()),
            new ResponseTrainingDTO(9L, PERIODIZATION.getId(),"D", "Nome do treino", LocalDateTime.now().toString(), LocalDateTime.now().toString())
    );
    public static final RequestUpdateTrainingDTO REQUEST_UPDATE_TRAINING = new RequestUpdateTrainingDTO(1L, 1, "Nome do treino");
    public static final List<Training> TRAININGS = List.of(
            new Training(1L, TrainingType.A, "Nome do treino", false, LocalDateTime.now(), LocalDateTime.now(), PERIODIZATION),
            new Training(2L, TrainingType.B, "Nome do treino", false, LocalDateTime.now(), LocalDateTime.now(), PERIODIZATION),
            new Training(3L, TrainingType.C, "Nome do treino", false, LocalDateTime.now(), LocalDateTime.now(), PERIODIZATION),
            new Training(4L, TrainingType.D, "Nome do treino", false, LocalDateTime.now(), LocalDateTime.now(), PERIODIZATION),
            new Training(5L, TrainingType.A, "Nome do treino", false, LocalDateTime.now(), LocalDateTime.now(), PERIODIZATION),
            new Training(6L, TrainingType.B, "Nome do treino", false, LocalDateTime.now(), LocalDateTime.now(), PERIODIZATION),
            new Training(7L, TrainingType.C, "Nome do treino", false, LocalDateTime.now(), LocalDateTime.now(), PERIODIZATION),
            new Training(8L, TrainingType.D, "Nome do treino", false, LocalDateTime.now(), LocalDateTime.now(), PERIODIZATION)
    );
}
