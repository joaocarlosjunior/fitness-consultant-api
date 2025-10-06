package br.com.fitnessconsultant.domain.repository;

import br.com.fitnessconsultant.domain.entities.ExerciseName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseNameRepository extends JpaRepository<ExerciseName, Long> {
    boolean existsByExerciseNameIgnoreCase(String name);
}
