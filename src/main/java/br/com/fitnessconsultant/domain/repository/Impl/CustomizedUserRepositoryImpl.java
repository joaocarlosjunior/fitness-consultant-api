package br.com.fitnessconsultant.domain.repository.Impl;

import br.com.fitnessconsultant.domain.repository.CustomizedUserRepository;
import br.com.fitnessconsultant.domain.repository.ExerciseRepository;
import br.com.fitnessconsultant.domain.repository.PeriodizationRepository;
import br.com.fitnessconsultant.domain.repository.TrainingRepository;
import jakarta.persistence.EntityManager;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository<Long> {

    private final PeriodizationRepository periodizationRepository;
    private final TrainingRepository trainingRepository;
    private final ExerciseRepository exerciseRepository;
    private final EntityManager entityManager;

    public CustomizedUserRepositoryImpl(PeriodizationRepository periodizationRepository, TrainingRepository trainingRepository, ExerciseRepository exerciseRepository, EntityManager entityManager) {
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
                .setParameter("userId",userId)
                .executeUpdate();
    }
}
