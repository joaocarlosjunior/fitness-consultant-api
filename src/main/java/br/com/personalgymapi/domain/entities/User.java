package br.com.personalgymapi.domain.entities;

import br.com.personalgymapi.domain.enums.Role;
import br.com.personalgymapi.validation.ValidIsActive;
import br.com.personalgymapi.validation.ValidRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Long id;

    @Column(name = "first_name", length = 15, nullable = false)
    @NotBlank(message = "{campo.user.first-name}")
    private String firstName;

    @Column(name = "last_name", length = 20, nullable = false)
    @NotBlank(message = "{campo.user.last-name}")
    private String lastName;

    @Column(name = "email", length = 50, unique = true, nullable = false)
    @NotBlank(message = "")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "")
    @JsonIgnore
    private String password;

    @Column(name = "phone",length = 20, unique = true)
    @NotBlank(message = "")
    private String phone;

    @Column(name = "is_active")
    @ValidIsActive
    private boolean isActive;

    @Column(name = "role_user", length = 20)
    @Enumerated(EnumType.STRING)
    @ValidRole
    private Role role;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Training> workouts;
}
