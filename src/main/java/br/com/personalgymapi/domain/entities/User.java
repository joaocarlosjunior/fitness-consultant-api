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

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "TBL_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME", length = 30)
    @NotBlank(message = "{campo.user.first-name}")
    private String firstName;

    @Column(name = "LAST_NAME", length = 30)
    @NotBlank(message = "{campo.user.last-name}")
    private String lastName;

    @Column(name = "EMAIL", unique = true)
    @NotBlank(message = "")
    private String email;

    @Column(name = "PASSWORD")
    @NotBlank(message = "")
    @JsonIgnore
    private String senha;

    @Column(name = "PHONE", unique = true)
    @NotBlank(message = "")
    private String phone;

    @Column(name = "IS_ACTIVE")
    @ValidIsActive
    private boolean isActive;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    @ValidRole
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Training> workouts;
}
