package br.com.personalgymapi.domain.repository;

import br.com.personalgymapi.domain.entities.Periodization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodizationRepository extends JpaRepository<Periodization, Long> {
}
