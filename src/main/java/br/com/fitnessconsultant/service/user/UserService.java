package br.com.fitnessconsultant.service.user;

import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserPeriodizationInfoDTO;

import java.util.List;

public interface UserService {
    void create(RequestUserDTO requestUserDTO);

    ResponseUserDTO findById(Long id);

    List<ResponseUserDTO> list();

    ResponseUserDTO update(Long id, UpdateUserDTO updateUserDTO);

    boolean setActiveUser(Long id);

    boolean setDisableUser(Long id);

    List<UserPeriodizationInfoDTO> getAllUserTraining(Long id);
}
