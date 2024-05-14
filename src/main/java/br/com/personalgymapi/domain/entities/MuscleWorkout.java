package br.com.personalgymapi.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "TBL_MUSCULO_TREINO")
public class MuscleWorkout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TREINO_ID", referencedColumnName = "ID", nullable = false)
    private Training training;

    @ManyToOne
    @JoinColumn(name = "MUSCULO_ID", referencedColumnName = "ID", nullable = false)
    private Muscule muscule;
}
