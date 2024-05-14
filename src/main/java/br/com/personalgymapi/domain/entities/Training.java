package br.com.personalgymapi.domain.entities;

import br.com.personalgymapi.domain.enums.TrainingType;
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
@Table(name = "TBL_TREINO")
public class Training {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TIPO_TREINO")
    @NotBlank(message = "Campo Treino Obrigatório")
    @Enumerated(value = EnumType.STRING)
    private TrainingType treino;

    @Column(name = "IS_DONE")
    private boolean isDone = false;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "training")
    private List<Exercise> exercises;

    @OneToMany(mappedBy = "training")
    private List<Heating> heatings;
}
