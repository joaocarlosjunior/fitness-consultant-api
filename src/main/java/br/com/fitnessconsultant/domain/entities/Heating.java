package br.com.fitnessconsultant.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "tbl_heating")
public class Heating{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_heating", nullable = false)
    private Long id;

    @Column(name = "series")
    private Integer series;

    @Column(name = "repetitions", length = 10)
    private String repetitions;

    @Column(name = "initial_load")
    private Integer initialLoad;

    @Column(name = "final_load")
    private Integer finalLoad;

    @Column(name = "method_exercise", length = 20)
    private String method;

    @ManyToOne
    @JoinColumn(name = "id_exercise_name", nullable = false)
    private ExerciseName exerciseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_training", nullable = false)
    private Training training;
}
