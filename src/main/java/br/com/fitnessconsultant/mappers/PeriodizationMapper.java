package br.com.fitnessconsultant.mappers;

import br.com.fitnessconsultant.domain.entities.Periodization;
import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PeriodizationMapper {

    public ResponsePeriodizationDTO toDto(Periodization periodization){
        if(periodization == null){
            return null;
        }
        return ResponsePeriodizationDTO
                .builder()
                .idPeriodization(periodization.getId())
                .name(periodization.getName())
                .numberWeeks(periodization.getNumberWeeks())
                .createdAt(DateUtils.formatDate(periodization.getCreatedAt()))
                .startDate(DateUtils.checkUpdateDate(periodization.getStarDate()))
                .build();
    }

    public Periodization toEntity(RequestPeriodizationDTO dto, User user){
        if(dto == null){
            return null;
        }
        return Periodization.builder()
                .name(dto.getName())
                .numberWeeks(dto.getNumberWeeks())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
