package br.com.personalgymapi.domain.repository;

import br.com.personalgymapi.domain.entities.Exercise;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query(value = "select e from Exercise e where e.training.id=:id")
    List<Exercise> getAllExercisesByIdTraining(@PathParam("id") Long id);
}
