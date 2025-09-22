package br.com.fitnessconsultant.common;

import br.com.fitnessconsultant.dto.training.RequestTrainingDTO;
import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;

import java.util.Arrays;
import java.util.List;

public class TrainingConstants {
    public static final RequestTrainingDTO TRAINING = new RequestTrainingDTO(1L, 1, "Nome do treino");
    public static final ResponseTrainingDTO RESPONSE_TRAINING = new ResponseTrainingDTO(1L, 1L,"A", "Nome do treino", "2025-09-16T15:42:30", "2025-09-16T15:42:30");
    public static final List<ResponseTrainingDTO> RESPONSE_TRAININGS = Arrays.asList(
            new ResponseTrainingDTO(1L, 1L,"A", "Nome do treino", "2025-09-16T15:42:30", "2025-09-16T15:42:30"),
            new ResponseTrainingDTO(2L, 1L,"B", "Nome do treino", "2025-09-16T15:42:30", "2025-09-16T15:42:30"),
            new ResponseTrainingDTO(3L, 1L,"C", "Nome do treino", "2025-09-16T15:42:30", "2025-09-16T15:42:30"),
            new ResponseTrainingDTO(4L, 1L,"D", "Nome do treino", "2025-09-16T15:42:30", "2025-09-16T15:42:30"),
            new ResponseTrainingDTO(5L, 2L,"A", "Nome do treino", "2025-09-16T15:42:30", "2025-09-16T15:42:30"),
            new ResponseTrainingDTO(6L, 2L,"B", "Nome do treino", "2025-09-16T15:42:30", "2025-09-16T15:42:30"),
            new ResponseTrainingDTO(7L, 2L,"C", "Nome do treino", "2025-09-16T15:42:30", "2025-09-16T15:42:30"),
            new ResponseTrainingDTO(9L, 2L,"D", "Nome do treino", "2025-09-16T15:42:30", "2025-09-16T15:42:30")
    );
}
