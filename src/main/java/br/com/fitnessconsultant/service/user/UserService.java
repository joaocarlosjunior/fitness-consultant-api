package br.com.fitnessconsultant.service.user;

import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserPeriodizationInfoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    void create(RequestUserDTO requestUserDTO, String siteUrl);

    ResponseEntity<ResponseUserDTO> findById(Long id);

    ResponseEntity<List<ResponseUserDTO>> list();

    ResponseEntity<ResponseUserDTO> update(Long id, UpdateUserDTO updateUserDTO);

    ResponseEntity<Map<String, String>>  setActiveUser(Long id);

    ResponseEntity<Map<String, String>> setDisableUser(Long id);

    ResponseEntity<List<UserPeriodizationInfoDTO>> getAllUserTraining(Long id);
}
