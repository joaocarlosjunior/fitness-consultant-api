package br.com.personalgymapi.service.user.impl;

import br.com.personalgymapi.domain.entities.User;
import br.com.personalgymapi.domain.enums.Role;
import br.com.personalgymapi.domain.repository.UserRepository;
import br.com.personalgymapi.dto.user.RecoveryUserDTO;
import br.com.personalgymapi.dto.user.RegisterUserDTO;
import br.com.personalgymapi.dto.user.UpdateUserDTO;
import br.com.personalgymapi.exception.InfoAlreadyExistsException;
import br.com.personalgymapi.exception.UserNotFoundException;
import br.com.personalgymapi.mapper.user.UserMapper;
import br.com.personalgymapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    public RecoveryUserDTO addUser(RegisterUserDTO registerUserDTO) {

        checkEmailAlreadyExists(registerUserDTO.getEmail());

        checkPhoneAlreadyExists(registerUserDTO.getPhone());

        User newUser = User
                .builder()
                .firstName(registerUserDTO.getFirstName())
                .lastName(registerUserDTO.getLastName())
                .email(registerUserDTO.getEmail().toLowerCase())
                .password(registerUserDTO.getPassword())
                .phone(registerUserDTO.getPhone())
                .isActive(false)
                .role(Role.fromValue(registerUserDTO.getRole()))
                .createdAt(LocalDateTime.now())
                .build();

        User userCreated = userRepository.save(newUser);

        return RecoveryUserDTO
                .builder()
                .firstName(userCreated.getFirstName())
                .lastName(userCreated.getLastName())
                .email(userCreated.getEmail())
                .phone(userCreated.getPhone())
                .role(userCreated.getRole())
                .created_at(formatDate(userCreated.getCreatedAt()))
                .build();
    }

    @Transactional(readOnly = true)
    public RecoveryUserDTO getUserById(Long id) {
        return userRepository
                .findById(id)
                .map(user -> RecoveryUserDTO.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .created_at(formatDate(user.getCreatedAt()))
                        .updated_at(checkUpdateDate(user.getUpdatedAt()))
                        .build())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public void deletedById(Long id) {
        userRepository
                .findById(id)
                .map(user -> {
                            userRepository.delete(user);
                            return Void.class;
                        }
                )
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public RecoveryUserDTO update(Long id, UpdateUserDTO updateUserDTO) {
        User user =
                userRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        if (!updateUserDTO.getEmail().toLowerCase().equals(user.getEmail())) {
            checkEmailAlreadyExists(updateUserDTO.getEmail());
        }

        if (!updateUserDTO.getPhone().equals(user.getPhone())) {
            checkPhoneAlreadyExists(updateUserDTO.getPhone());
        }

        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setEmail(updateUserDTO.getEmail());
        user.setPhone(updateUserDTO.getPhone());
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);

        return RecoveryUserDTO
                .builder()
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                .email(updatedUser.getEmail())
                .phone(updatedUser.getPhone())
                .created_at(formatDate(updatedUser.getCreatedAt()))
                .updated_at(formatDate(updatedUser.getUpdatedAt()))
                .build();
    }

    @Transactional(readOnly = true)
    public List<RecoveryUserDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map((user -> {
                    return RecoveryUserDTO
                            .builder()
                            .id(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .phone(user.getPhone())
                            .role(user.getRole())
                            .created_at(formatDate(user.getCreatedAt()))
                            .updated_at(checkUpdateDate(user.getUpdatedAt()))
                            .build();
                }))
                .collect(Collectors.toList());
    }

    private void checkEmailAlreadyExists(String email) {
        if (userRepository.findByEmail(email.toLowerCase()).isPresent()) {
            throw new InfoAlreadyExistsException("Email já cadastrado");
        }
    }

    private void checkPhoneAlreadyExists(String phone) {
        if (userRepository.findByPhone(phone).isPresent()) {
            throw new InfoAlreadyExistsException("Telefone já cadastrado");
        }
    }

    private String checkUpdateDate(LocalDateTime date){
        return date!= null? formatDate(date): null;
    }

    private String formatDate(LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
