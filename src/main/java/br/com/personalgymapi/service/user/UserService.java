package br.com.personalgymapi.service.user;

import br.com.personalgymapi.domain.entities.User;
import br.com.personalgymapi.dto.user.RecoveryUserDTO;
import br.com.personalgymapi.dto.user.RegisterUserDTO;
import br.com.personalgymapi.dto.user.UpdateUserDTO;

import java.util.List;

public interface UserService {
    RecoveryUserDTO addUser(RegisterUserDTO registerUserDTO);

    RecoveryUserDTO getUserById(Long id);

    List<RecoveryUserDTO> getAllUsers();

    RecoveryUserDTO update(Long id, UpdateUserDTO updateUserDTO);

    void deletedById(Long id);
}
