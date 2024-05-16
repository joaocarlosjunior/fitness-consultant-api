package br.com.personalgymapi.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "TEMPO_TREINO")
public class TimeTraining {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "TEMPO_INICIAL")
    private String startTime;

    @Column(name = "TEMPO_FINAL")
    private String finalTime;

    @Column(name = "DATA_TREINO")
    private String date;
}
