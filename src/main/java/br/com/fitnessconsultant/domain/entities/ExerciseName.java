package br.com.fitnessconsultant.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NotBlank(message = "Campo Nome Exercício obrigatório")
    private String exerciseName;

    @ManyToOne
    @JoinColumn(name = "id_muscle_group", nullable = false)
    private MuscleGroup muscleGroup;
}
