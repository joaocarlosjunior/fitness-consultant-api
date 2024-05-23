package br.com.personalgymapi.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_periodization")
public class Periodization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_periodization",nullable = false)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Nome da periodização obrigatorio")
    private String name;

    @Column(name = "number_weeks")
    private Integer numberWeeks;

    @Column(name = "start_date")
    private LocalDateTime starDate;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @OneToMany(mappedBy = "periodization")
    private List<Training> trainings;
}