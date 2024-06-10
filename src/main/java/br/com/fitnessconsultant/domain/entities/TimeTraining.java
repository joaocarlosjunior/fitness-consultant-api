package br.com.fitnessconsultant.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tbl_training_time")
public class TimeTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_training_time", nullable = false)
    private Long id;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "final_time")
    private Timestamp finalTime;

    @Column(name = "training_date")
    private LocalDate date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_training")
    private Training training;
}
