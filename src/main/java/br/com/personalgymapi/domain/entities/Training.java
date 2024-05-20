package br.com.personalgymapi.domain.entities;

import br.com.personalgymapi.domain.enums.TrainingType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "tbl_training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_training", nullable = false)
    private Long id;

    @Column(name = "training_type", length = 8, nullable = false)
    @NotBlank(message = "Campo Tipo Treino Obrigatório")
    @Enumerated(value = EnumType.ORDINAL)
    private TrainingType trainingType;

    @Column(name = "is_done")
    private boolean isDone = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
    private Set<Exercise> exercises;

    @OneToMany(mappedBy = "training", fetch = FetchType.EAGER)
    private Set<Heating> warmups;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_musclegroup_training",
            joinColumns = @JoinColumn(name = "id_training"),
            inverseJoinColumns = @JoinColumn(name = "id_muscle_group")
    )
    private Set<MuscleGroup> muscleGroups;

    @OneToMany(mappedBy = "training")
    private Set<TimeTraining> timeTrainings;
}
