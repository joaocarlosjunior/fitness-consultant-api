package br.com.fitnessconsultant.domain.repository;

import br.com.fitnessconsultant.domain.entities.Training;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training,Long> {
    @Query(value = "select t from Training t where t.periodization.id=:id")
    List<Training> getAllTrainingByIdPeriodization(@PathParam("id") Long id);

    @Query("""
        delete from Training t
        where t.periodization.id in (
            select p.id from Periodization p
            where p.user.id = :userId
        )
        """)
    @Modifying
    void deleteAllByUserId(@Param("userId") Long userId);

    boolean existsTrainingsById(Long id);
}
