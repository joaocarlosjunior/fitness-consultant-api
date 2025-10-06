package br.com.fitnessconsultant.domain.repository;

import br.com.fitnessconsultant.dto.user.usertraininginfo.UserPeriodizationInfoDTO;

import java.util.List;

public interface CustomizedUserRepository<T> {
    List<UserPeriodizationInfoDTO> getAllUserTrainingInfo(T userId);
}
