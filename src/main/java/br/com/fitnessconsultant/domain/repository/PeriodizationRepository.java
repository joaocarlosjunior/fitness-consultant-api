package br.com.fitnessconsultant.domain.repository;

import br.com.fitnessconsultant.domain.entities.Periodization;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodizationRepository extends JpaRepository<Periodization, Long> {
    @Query(value = "select p from Periodization p where p.user.id=:id")
    List<Periodization> getAllPeriodizationByIdUser(@PathParam("id") Long id);
}
