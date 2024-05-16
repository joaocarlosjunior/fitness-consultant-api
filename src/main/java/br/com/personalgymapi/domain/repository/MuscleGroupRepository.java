package br.com.personalgymapi.domain.repository;

import br.com.personalgymapi.domain.entities.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuscleGroupRepository extends JpaRepository<MuscleGroup,Long> {
}
