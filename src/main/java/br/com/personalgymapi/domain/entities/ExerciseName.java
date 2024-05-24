package br.com.personalgymapi.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_exercise_name")
public class ExerciseName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exercise_name", nullable = false)
    private Long id;

    @Column(name = "exercise_name", unique = true, length = 30)
    private String exerciseName;

    @OneToMany(mappedBy = "exerciseName")
    private Set<Exercise> exercises;

    @OneToMany(mappedBy = "exerciseName")
    private List<Heating> heatings;

    @ManyToOne
    @JoinColumn(name = "id_muscle_group", nullable = false)
    private MuscleGroup muscleGroup;
}
