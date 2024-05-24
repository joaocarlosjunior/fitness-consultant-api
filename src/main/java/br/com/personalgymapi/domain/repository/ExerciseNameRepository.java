package br.com.personalgymapi.domain.repository;

import br.com.personalgymapi.domain.entities.ExerciseName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseNameRepository extends JpaRepository<ExerciseName, Long> {
    Optional<ExerciseName> findByExerciseNameIgnoreCase(String name);

    boolean existsByExerciseNameIgnoreCase(String name);
}
