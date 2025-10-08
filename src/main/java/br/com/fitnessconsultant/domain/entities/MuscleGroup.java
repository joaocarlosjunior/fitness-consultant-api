package br.com.fitnessconsultant.domain.entities;

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
@Table(name = "tbl_muscle_group")
public class MuscleGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_muscle_group",nullable = false)
    private Long id;

    @Column(name = "name_group", length = 100, nullable = false, unique = true)
    @NotBlank(message = "Campo Nome Grupo Muscular obrigatório")
    private String name;

    @OneToMany(mappedBy = "muscleGroup")
    private List<ExerciseName> exerciseNames;

    public MuscleGroup(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

