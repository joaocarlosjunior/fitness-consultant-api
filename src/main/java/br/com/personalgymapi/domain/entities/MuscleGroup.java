package br.com.personalgymapi.domain.entities;

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
@Table(name = "tbl_muscle_group")
public class MuscleGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_muscle_group",nullable = false)
    private Long id;

    @Column(name = "name_group", length = 50, nullable = false)
    @NotBlank(message = "Campo Nome Grupo Muscular é obrigatório")
    private String name;
}

