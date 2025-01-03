package br.com.fitnessconsultant.domain.entities;

import br.com.fitnessconsultant.domain.enums.TrainingType;
import br.com.fitnessconsultant.validation.ValidTrainingType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_training", nullable = false)
    private Long id;

    @Column(name = "training_type", length = 1)
    @Enumerated(EnumType.STRING)
    @ValidTrainingType
    @NotNull(message = "Campo Tipo Treino é obrigatório")
    private TrainingType trainingType;

    @Column(name = "training_name", length = 20)
    @NotBlank(message = "Campo Nome Treino é obrigatório")
    private String trainingName;

    @Column(name = "is_done")
    private boolean isDone = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_periodization")
    private Periodization periodization;
}
