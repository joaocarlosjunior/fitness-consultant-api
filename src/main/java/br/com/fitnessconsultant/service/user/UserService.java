package br.com.fitnessconsultant.service.user;

import br.com.fitnessconsultant.dto.user.RecoveryUserDTO;
import br.com.fitnessconsultant.dto.user.RegisterUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;

import java.util.List;

public interface UserService {
    RecoveryUserDTO addUser(RegisterUserDTO registerUserDTO);

    RecoveryUserDTO getUserById(Long id);

    List<RecoveryUserDTO> getAllUsers();

    RecoveryUserDTO updateUser(Long id, UpdateUserDTO updateUserDTO);

    void deletedById(Long id);

    void setActiveUser(Long id);

    void setDisableUser(Long id);
}
