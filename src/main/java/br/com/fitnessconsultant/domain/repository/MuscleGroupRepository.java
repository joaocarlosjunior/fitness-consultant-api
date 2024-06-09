package br.com.fitnessconsultant.domain.repository;

import br.com.fitnessconsultant.domain.entities.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuscleGroupRepository extends JpaRepository<MuscleGroup,Long> {
    boolean existsByNameIgnoreCase(String name);
}
