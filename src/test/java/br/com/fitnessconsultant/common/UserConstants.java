package br.com.fitnessconsultant.common;

import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.enums.Role;
import br.com.fitnessconsultant.domain.enums.TrainingType;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserExerciseInfoDTO;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserPeriodizationInfoDTO;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserTrainingInfoDTO;

import java.time.LocalDateTime;
import java.util.List;

public class UserConstants {
    public static final List<User> LIST_USERS = List.of(
            new User(1L,
                    "Nome usuario A",
                    "Sobrenome usuario A",
                    "email_usuario1@email.com",
                    "12345",
                    "77999999991",
                    Role.ROLE_USER,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    true
            ),
            new User(2L,
                    "Nome usuario B",
                    "Sobrenome usuario B",
                    "email_usuario2@email.com",
                    "12345",
                    "77999999992",
                    Role.ROLE_USER,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    true
            ),
            new User(3L,
                    "Nome usuario C",
                    "Sobrenome usuario C",
                    "email_usuario3@email.com",
                    "12345",
                    "77999999993",
                    Role.ROLE_USER,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    true
            )
    );

    public static final List<UserExerciseInfoDTO> EXERCISE_INFO_LIST = List.of(
            new UserExerciseInfoDTO(1L, "Nome Exercicio A", 4, "Repeticoes", "Metodo Exercicio A", 20, 30),
            new UserExerciseInfoDTO(2L, "Nome Exercicio B", 5, "Repeticoes", "Metodo Exercicio B", 30, 40),
            new UserExerciseInfoDTO(3L, "Nome Exercicio C", 6, "Repeticoes", "Metodo Exercicio C", 40, 50),
            new UserExerciseInfoDTO(4L, "Nome Exercicio D", 7, "Repeticoes", "Metodo Exercicio D", 50, 60)
    );

    public static final List<UserTrainingInfoDTO> TRAINING_INFO_LIST = List.of(
            new UserTrainingInfoDTO(1L, "Nome Treino A", TrainingType.A.toString(), EXERCISE_INFO_LIST),
            new UserTrainingInfoDTO(2L, "Nome Treino B", TrainingType.B.toString(), EXERCISE_INFO_LIST),
            new UserTrainingInfoDTO(3L, "Nome Treino C", TrainingType.C.toString(), EXERCISE_INFO_LIST),
            new UserTrainingInfoDTO(4L, "Nome Treino D", TrainingType.D.toString(), EXERCISE_INFO_LIST)
            );

    public static final List<UserPeriodizationInfoDTO> PERIODIZATION_INFO_LIST = List.of(
            new UserPeriodizationInfoDTO(
                    1L,
                    "Nome Periodizacao 1",
                    6,
                    TRAINING_INFO_LIST
            )
    );
}
