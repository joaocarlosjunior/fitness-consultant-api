package br.com.fitnessconsultant.domain.repository;

import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CustomizedUserRepository<Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhone(String phone);

    boolean existsUsersById(Long id);

    @Query("select u from User u where u.role=:roleUser")
    List<User> findAllUserRoleUser(@Param("roleUser") Role roleUser);

}
