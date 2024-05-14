package br.com.personalgymapi.service.user;

import br.com.personalgymapi.dto.user.RecoveryUserDTO;
import br.com.personalgymapi.dto.user.RegisterUserDTO;

import java.util.List;

public interface UserService {
    RecoveryUserDTO addUser(RegisterUserDTO registerUserDTO);

    RecoveryUserDTO getUserById(Long id);

    List<RecoveryUserDTO> getAllUsers();

    void update(Long id, RegisterUserDTO registerUserDTO);

    void deletedById(Long id);
}
