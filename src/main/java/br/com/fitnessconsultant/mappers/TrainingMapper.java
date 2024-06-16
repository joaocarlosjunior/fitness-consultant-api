package br.com.fitnessconsultant.mappers;

import br.com.fitnessconsultant.domain.entities.Periodization;
import br.com.fitnessconsultant.domain.entities.Training;
import br.com.fitnessconsultant.domain.enums.TrainingType;
import br.com.fitnessconsultant.dto.training.RequestTrainingDTO;
import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;
import br.com.fitnessconsultant.utils.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class TrainingMapper {

    public ResponseTrainingDTO toDto(Training training){
        if(training == null){
            return null;
        }
        return ResponseTrainingDTO
                .builder()
                .trainingType(training.getTrainingType().name())
                .trainingName(training.getTrainingName())
                .createdAt(DateUtils.formatDate(training.getCreatedAt()))
                .updatedAt(DateUtils.checkUpdateDate(training.getUpdatedAt()))
                .idPeriodization(training.getPeriodization().getId())
                .build();
    }

    public Training toEntity(RequestTrainingDTO dto, Periodization periodization) {
        if(dto == null){
            return null;
        }
        return Training
                .builder()
                .trainingName(dto.trainingName())
                .trainingType(TrainingType.fromValue(dto.trainingType()))
                .periodization(periodization)
                .isDone(false)
                .build();
    }
}
