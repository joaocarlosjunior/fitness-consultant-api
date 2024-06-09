package br.com.fitnessconsultant.service.user;

import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;

import java.util.List;

public interface UserService {
    ResponseUserDTO create(RequestUserDTO requestUserDTO);

    ResponseUserDTO findById(Long id);

    List<ResponseUserDTO> list();

    ResponseUserDTO update(Long id, UpdateUserDTO updateUserDTO);

    void delete(Long id);

    void setActiveUser(Long id);

    void setDisableUser(Long id);
}
