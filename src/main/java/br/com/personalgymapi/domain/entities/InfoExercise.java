package br.com.personalgymapi.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@MappedSuperclass
public abstract class InfoExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SERIES")
    private Integer series;

    @Column(name = "REPETICOES")
    private String repetitions;

    @Column(name = "CARGA_INICIAL")
    private Integer initialLoad;

    @Column(name = "CARGA_FINAL")
    private Integer finalLoad;

    @Column(name = "METODO")
    private String metodo;
}
