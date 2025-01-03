package br.com.fitnessconsultant.dto.user.usertraininginfo;

import java.util.List;

public record UserPeriodizationInfoDTO(
        Long id,
        String name,
        Integer number_weeks,
        List<UserTrainingInfoDTO> trainings){}
