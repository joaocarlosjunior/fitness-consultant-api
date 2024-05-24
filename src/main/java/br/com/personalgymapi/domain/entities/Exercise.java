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
@Table(name = "tbl_exercise")
public class Exercise{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exercise", nullable = false)
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

    @ManyToOne
    @JoinColumn(name = "id_training", nullable = false)
    private Training training;


}
