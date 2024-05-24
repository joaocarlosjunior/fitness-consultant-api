package br.com.personalgymapi.service.user.impl;

import br.com.personalgymapi.domain.entities.Periodization;
import br.com.personalgymapi.domain.entities.User;
import br.com.personalgymapi.domain.enums.Role;
import br.com.personalgymapi.domain.repository.UserRepository;
import br.com.personalgymapi.dto.periodization.RecoveryPeriodizationDTO;
import br.com.personalgymapi.dto.user.RecoveryUserDTO;
import br.com.personalgymapi.dto.user.RegisterUserDTO;
import br.com.personalgymapi.dto.user.UpdateUserDTO;
import br.com.personalgymapi.exception.InfoAlreadyExistsException;
import br.com.personalgymapi.exception.UserNotFoundException;
import br.com.personalgymapi.service.user.UserService;
import br.com.personalgymapi.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    public RecoveryUserDTO addUser(RegisterUserDTO registerUserDTO) {

        checkEmailExists(registerUserDTO.getEmail());

        checkPhoneExists(registerUserDTO.getPhone());

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
                .isActive(userCreated.isActive())
                .created_at(DateUtils.formatDate(userCreated.getCreatedAt()))
                .build();
    }

    public RecoveryUserDTO getUserById(Long id) {
        User user = getUser(id);

        return RecoveryUserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole())
                .isActive(user.isActive())
                .periodizations(getAllPeriodization(user.getPeriodizations()))
                .created_at(DateUtils.formatDate(user.getCreatedAt()))
                .updated_at(DateUtils.checkUpdateDate(user.getUpdatedAt()))
                .build();
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

    public void setActiveUser(Long id) {
        User user = getUser(id);

        if(!user.isActive()){
            user.setActive(true);
            userRepository.save(user);
        }
    }

    public void setDisableUser(Long id) {
        User user = getUser(id);

        if(user.isActive()){
            user.setActive(false);
            userRepository.save(user);
        }
    }

    @Transactional
    public RecoveryUserDTO updateUser(Long id, UpdateUserDTO updateUserDTO) {
        User user = getUser(id);

        String currentEmail = user.getEmail();
        String newEmail = updateUserDTO.getEmail();

        if(!newEmail.equals(currentEmail)){
            checkEmailExists(newEmail);
        }

        String newPhone = updateUserDTO.getPhone();
        String currentPhone = user.getPhone();

        if (!newPhone.equals(currentPhone)) {
            checkPhoneExists(newPhone);
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
                .created_at(DateUtils.formatDate(updatedUser.getCreatedAt()))
                .updated_at(DateUtils.formatDate(updatedUser.getUpdatedAt()))
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
                            .isActive(user.isActive())
                            .created_at(DateUtils.formatDate(user.getCreatedAt()))
                            .updated_at(DateUtils.checkUpdateDate(user.getUpdatedAt()))
                            .build();
                }))
                .collect(Collectors.toList());
    }

    private List<RecoveryPeriodizationDTO> getAllPeriodization(List<Periodization> periodizations) {
        if(periodizations.isEmpty()){
            return null;
        }

        return periodizations.stream()
                .map(periodization -> {
                    return RecoveryPeriodizationDTO
                            .builder()
                            .id(periodization.getId())
                            .idUser(periodization.getUser().getId())
                            .name(periodization.getName())
                            .numberWeeks(periodization.getNumberWeeks())
                            .startDate(DateUtils.checkUpdateDate(periodization.getStarDate()))
                            .build();
                }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    protected User getUser(Long id){
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));
    }

    @Transactional(readOnly = true)
    protected void checkEmailExists(String email){
        boolean emailExists = userRepository
                .existsByEmailIgnoreCase(email);

        if(emailExists){
            throw new InfoAlreadyExistsException("Email já cadastrado");
        }
    }

    @Transactional(readOnly = true)
    protected void checkPhoneExists(String phone){
        boolean phoneExists = userRepository
                .existsByPhone(phone);

        if(phoneExists){
            throw new InfoAlreadyExistsException("Telefone já cadastrado");
        }
    }

}
