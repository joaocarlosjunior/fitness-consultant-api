package br.com.personalgymapi.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "TBL_AQUECIMENTO")
public class Heating extends InfoExercise {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "NOME_EXERCISIO_ID", referencedColumnName = "ID")
    private NameExercise nameExercise;

    @ManyToOne
    @JoinColumn(name = "TREINO_ID", referencedColumnName = "ID")
    private Training training;
}
