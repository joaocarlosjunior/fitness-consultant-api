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
@Table(name = "TBL_MUSCULO")
public class Muscule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME")
    @NotBlank(message = "Campo Nome Musculo é obrigatório")
    private String nameMuscule;

    @ManyToOne
    @JoinColumn(name = "GRUPO_MUSCULAR_ID", referencedColumnName = "ID", nullable = false)
    private MusculeGroup musculeGroup;
}
