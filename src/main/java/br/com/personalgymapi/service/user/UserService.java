package br.com.personalgymapi.service.user;

import br.com.personalgymapi.domain.entities.User;
import br.com.personalgymapi.dto.user.RecoveryUserDTO;
import br.com.personalgymapi.dto.user.RegisterUserDTO;
import br.com.personalgymapi.dto.user.UpdateUserDTO;
import org.springframework.web.bind.annotation.PathVariable;

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
