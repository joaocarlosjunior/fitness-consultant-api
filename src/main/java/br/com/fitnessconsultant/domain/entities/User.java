package br.com.fitnessconsultant.domain.entities;

import br.com.fitnessconsultant.domain.enums.Role;
import br.com.fitnessconsultant.validation.ValidIsActive;
import br.com.fitnessconsultant.validation.ValidRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_user")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Long id;

    @Column(name = "first_name", length = 50, nullable = false)
    @NotBlank(message = "{field.user.first-name}")
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    @NotBlank(message = "{field.user.last-name}")
    private String lastName;

    @Column(name = "email", length = 50, unique = true, nullable = false)
    @NotBlank(message = "{field.user.email}")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "{field.user.password}")
    @JsonIgnore
    private String password;

    @Column(name = "phone",length = 20, unique = true)
    @NotBlank(message = "{field.user.phone}")
    private String phone;

    @Column(name = "is_active")
    @ValidIsActive
    private boolean isActive;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ValidRole
    private Role role;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled){
        isEnabled = enabled;
    }
}
