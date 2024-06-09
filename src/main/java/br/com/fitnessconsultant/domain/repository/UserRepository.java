package br.com.fitnessconsultant.domain.repository;

import br.com.fitnessconsultant.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CustomizedUserRepository<Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhone(String phone);

    @Query("select u from User u where u.isActive=:isActive")
    List<User> findAllUserIsActive(@Param("isActive") boolean isActive);

}
