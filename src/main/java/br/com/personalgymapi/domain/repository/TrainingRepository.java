package br.com.personalgymapi.domain.repository;

import br.com.personalgymapi.domain.entities.Periodization;
import br.com.personalgymapi.domain.entities.Training;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training,Long> {
    @Query(value = "select t from Training t where t.periodization.id=:id")
    List<Training> getAllTrainingByIdPeriodization(@PathParam("id") Long id);
}
