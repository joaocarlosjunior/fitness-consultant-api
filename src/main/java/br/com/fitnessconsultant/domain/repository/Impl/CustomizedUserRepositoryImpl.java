package br.com.fitnessconsultant.domain.repository.Impl;

import br.com.fitnessconsultant.domain.enums.TrainingType;
import br.com.fitnessconsultant.domain.repository.CustomizedUserRepository;
import br.com.fitnessconsultant.domain.repository.ExerciseRepository;
import br.com.fitnessconsultant.domain.repository.PeriodizationRepository;
import br.com.fitnessconsultant.domain.repository.TrainingRepository;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserExerciseInfoDTO;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserPeriodizationInfoDTO;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserTrainingInfoDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;

import java.util.List;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository<Long> {

    private final PeriodizationRepository periodizationRepository;
    private final TrainingRepository trainingRepository;
    private final ExerciseRepository exerciseRepository;
    private final EntityManager entityManager;

    public CustomizedUserRepositoryImpl(PeriodizationRepository periodizationRepository,
                                        TrainingRepository trainingRepository,
                                        ExerciseRepository exerciseRepository,
                                        EntityManager entityManager) {
        this.periodizationRepository = periodizationRepository;
        this.trainingRepository = trainingRepository;
        this.exerciseRepository = exerciseRepository;
        this.entityManager = entityManager;
    }

    @Override
    public void deletedById(Long userId) {
        exerciseRepository.deleteAllByUserId(userId);
        trainingRepository.deleteAllByUserId(userId);
        periodizationRepository.deleteAllByUserId(userId);

        entityManager.createQuery("""
                                    delete from User
                                    where id = :userId
                        """)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public List<UserPeriodizationInfoDTO> getAllUserTrainingInfo(Long userId) {
        List<Tuple> periodizations = entityManager.createQuery(
                        "select p.id as id, p.name as name, p.numberWeeks as number_weeks " +
                                "from Periodization p " +
                                "where p.user.id=:userId ", Tuple.class)
                .setParameter("userId", userId)
                .getResultList();

        if(periodizations.isEmpty()){
            return null;
        }

        List<UserPeriodizationInfoDTO> dto = periodizations.stream()
                .map(periodization ->
                        new UserPeriodizationInfoDTO(
                                (Long) periodization.get("id"),
                                (String) periodization.get("name"),
                                (Integer) periodization.get("number_weeks"),
                                getUserTraining((Long) periodization.get("id"))
                        )
                ).toList();

        return dto;
    }

    private List<UserTrainingInfoDTO> getUserTraining(Long idPeriodization) {
        System.out.println("Peridoização:" + idPeriodization);
        List<Tuple> trainings = entityManager.createQuery(
                        "select t.id as id, t.trainingName as training_name, t.trainingType as training_type " +
                                "from Training t " +
                                "where t.periodization.id = :id", Tuple.class)
                .setParameter("id", idPeriodization)
                .getResultList();

        if(trainings.isEmpty()){
            return null;
        }

        List<UserTrainingInfoDTO> dto = trainings.stream()
                .map(training -> new UserTrainingInfoDTO(
                                (Long) training.get("id"),
                                (String) training.get("training_name"),
                                TrainingType.getName((TrainingType) training.get("training_type")),
                                getUserExercise((Long) training.get("id"))
                        )
                ).toList();

        return dto;
    }

    private List<UserExerciseInfoDTO> getUserExercise(Long idTraining) {
        System.out.println(idTraining);
        List<Tuple> exercises = entityManager.createQuery(
                        "select e.id as id, en.exerciseName as exercise_name, e.series as series, e.repetitions as repetitions, " +
                                "e.method as method_exercise, e.initialLoad as initial_load, " +
                                "e.finalLoad as final_load " +
                                "from Exercise e " +
                                "join ExerciseName en " +
                                "on e.exerciseName.id=en.id " +
                                "where e.training.id = :id ", Tuple.class)
                .setParameter("id", idTraining)
                .getResultList();

        if(exercises.isEmpty()){
            return null;
        }

        List<UserExerciseInfoDTO> dto = exercises.stream()
                .map(exercise -> new UserExerciseInfoDTO(
                                (Long) exercise.get("id"),
                                (String) exercise.get("exercise_name"),
                                (Integer) exercise.get("series"),
                                (String) exercise.get("repetitions"),
                                (String) exercise.get("method_exercise"),
                                (Integer) exercise.get("initial_load"),
                                (Integer) exercise.get("final_load")
                        )
                ).toList();

        return dto;
    }
}