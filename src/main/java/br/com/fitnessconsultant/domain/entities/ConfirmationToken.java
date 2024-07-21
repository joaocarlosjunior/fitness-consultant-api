package br.com.fitnessconsultant.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor

@Entity
@Table(name = "tbl_confirmation_token")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token", nullable = false)
    private Long id;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_user")
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
        createdAt = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}
