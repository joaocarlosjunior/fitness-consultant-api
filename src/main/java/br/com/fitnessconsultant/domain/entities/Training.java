package br.com.fitnessconsultant.domain.entities;

import br.com.fitnessconsultant.domain.enums.TrainingType;
import br.com.fitnessconsultant.validation.ValidTrainingType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
@Table(name = "tbl_training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_training", nullable = false)
    private Long id;

    @Column(name = "training_type", length = 1)
    @Enumerated(EnumType.STRING)
    @ValidTrainingType
    private TrainingType trainingType;

    @Column(name = "training_name", length = 20)
    private String trainingName;

    @Column(name = "is_done")
    private boolean isDone = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "training", fetch = FetchType.LAZY)
    private Set<Exercise> exercises;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "training", fetch = FetchType.LAZY)
    private Set<Heating> warmups;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "training")
    private Set<TimeTraining> timeTrainings;

    @ManyToOne
    @JoinColumn(name = "id_periodization")
    private Periodization periodization;
}