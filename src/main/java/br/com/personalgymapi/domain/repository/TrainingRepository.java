package br.com.personalgymapi.domain.repository;

import br.com.personalgymapi.domain.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training,Long> {
}
