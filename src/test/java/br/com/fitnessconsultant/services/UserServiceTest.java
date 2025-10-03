package br.com.fitnessconsultant.services;

import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.enums.Role;
import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserPeriodizationInfoDTO;
import br.com.fitnessconsultant.exception.InfoAlreadyExistsException;
import br.com.fitnessconsultant.exception.UserNotFoundException;
import br.com.fitnessconsultant.mappers.UserMapper;
import br.com.fitnessconsultant.service.user.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static br.com.fitnessconsultant.common.UserConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @Test
    public void create_WithValidData_DoesNotThrowException() {
        String password = "12345";
        when(userRepository.existsByEmailIgnoreCase(NEW_USER_REQUEST.email())).thenReturn(false);
        when(userRepository.existsByPhone(NEW_USER_REQUEST.phone())).thenReturn(false);
        when(userMapper.toEntity(NEW_USER_REQUEST, password)).thenReturn(USER);

        assertThatCode(() -> userService.create(NEW_USER_REQUEST)).doesNotThrowAnyException();
        verify(userRepository).existsByEmailIgnoreCase(NEW_USER_REQUEST.email());
        verify(userRepository).existsByPhone(NEW_USER_REQUEST.phone());
        verify(userMapper).toEntity(NEW_USER_REQUEST, password);
        verify(userRepository).save(USER);
    }

    @Test
    public void create_WithUserEmailAlreadyRegistered_ThrowsException() {
        when(userRepository.existsByEmailIgnoreCase(NEW_USER_REQUEST.email())).thenReturn(true);

        assertThrows(InfoAlreadyExistsException.class, () -> userService.create(NEW_USER_REQUEST));
        verify(userRepository).existsByEmailIgnoreCase(NEW_USER_REQUEST.email());
    }

    @Test
    public void create_WithUserPhoneAlreadyRegistered_ThrowsException() {
        when(userRepository.existsByEmailIgnoreCase(NEW_USER_REQUEST.email())).thenReturn(false);
        when(userRepository.existsByPhone(NEW_USER_REQUEST.phone())).thenReturn(true);

        assertThrowsExactly(InfoAlreadyExistsException.class, () -> userService.create(NEW_USER_REQUEST));
        verify(userRepository).existsByEmailIgnoreCase(NEW_USER_REQUEST.email());
        verify(userRepository).existsByPhone(NEW_USER_REQUEST.phone());
    }

    @Test
    public void findById_WithExistingId_ReturnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER));
        when(userMapper.toDto(USER)).thenReturn(USER_RESPONSE);

        ResponseUserDTO sut = userService.findById(1L);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(USER_RESPONSE);
        verify(userRepository).findById(1L);
        verify(userMapper).toDto(USER);
    }

    @Test
    public void findById_WithNonExistingId_ReturnsNull() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrowsExactly(UserNotFoundException.class, () -> userService.findById(99L));
        verify(userRepository).findById(99L);
    }

    @Test
    public void setActiveUser_WithDisabledUser_ReturnsBoolean() {
        User userDisabled = new User(1L, "Nome usuario", "Sobrenome usuario", "email_usuario@email.com", "12345", "77999999999", Role.ROLE_USER, LocalDateTime.now(), LocalDateTime.now(), false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userDisabled));

        boolean sut = userService.setActiveUser(1L);

        assertThat(sut).isTrue();
        verify(userRepository).findById(1L);
        verify(userRepository).save(userDisabled);
    }

    @Test
    public void setActiveUser_WithAlreadyActiveUser_ReturnsBoolean() {
        User userActive = new User(1L, "Nome usuario", "Sobrenome usuario", "email_usuario@email.com", "12345", "77999999999", Role.ROLE_USER, LocalDateTime.now(), LocalDateTime.now(), true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userActive));

        boolean sut = userService.setActiveUser(1L);

        assertThat(sut).isFalse();
        verify(userRepository).findById(1L);
    }

    @Test
    public void setDisableUser_WithActiveUser_ReturnsBoolean() {
        User userActive = new User(1L, "Nome usuario", "Sobrenome usuario", "email_usuario@email.com", "12345", "77999999999", Role.ROLE_USER, LocalDateTime.now(), LocalDateTime.now(), true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userActive));

        boolean sut = userService.setDisableUser(1L);

        assertThat(sut).isTrue();
        verify(userRepository).findById(1L);
        verify(userRepository).save(userActive);
    }

    @Test
    public void setDisableUser_WithAlreadyDisabledUser_ReturnsBoolean() {
        User userDisabled = new User(1L, "Nome usuario", "Sobrenome usuario", "email_usuario@email.com", "12345", "77999999999", Role.ROLE_USER, LocalDateTime.now(), LocalDateTime.now(), false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userDisabled));

        boolean sut = userService.setDisableUser(1L);

        assertThat(sut).isFalse();
        verify(userRepository).findById(1L);
    }

    @Test
    public void update_UpdateAllUserData_ReturnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER));
        when(userRepository.existsByEmailIgnoreCase(UPDATE_USER.email())).thenReturn(false);
        when(userRepository.existsByPhone(UPDATE_USER.phone())).thenReturn(false);
        when(userRepository.save(USER)).thenReturn(USER);
        when(userMapper.toDto(USER)).thenReturn(USER_RESPONSE);

        ResponseUserDTO sut = userService.update(1L, UPDATE_USER);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(USER_RESPONSE);
        verify(userRepository).findById(1L);
        verify(userRepository).existsByEmailIgnoreCase(UPDATE_USER.email());
        verify(userRepository).existsByPhone(UPDATE_USER.phone());
        verify(userRepository).save(USER);
        verify(userMapper).toDto(USER);
    }

    @Test
    public void update_WithEmailExists_ThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER));
        when(userRepository.existsByEmailIgnoreCase(UPDATE_USER.email())).thenReturn(true);

        assertThrows(InfoAlreadyExistsException.class, () -> userService.update(1L, UPDATE_USER));
        verify(userRepository).findById(1L);
        verify(userRepository).existsByEmailIgnoreCase(UPDATE_USER.email());
    }

    @Test
    public void update_WithPhoneExists_ThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(USER));
        when(userRepository.existsByEmailIgnoreCase(UPDATE_USER.email())).thenReturn(false);
        when(userRepository.existsByPhone(UPDATE_USER.phone())).thenReturn(true);

        assertThrows(InfoAlreadyExistsException.class, () -> userService.update(1L, UPDATE_USER));
        verify(userRepository).findById(1L);
        verify(userRepository).existsByEmailIgnoreCase(UPDATE_USER.email());
        verify(userRepository).existsByPhone(UPDATE_USER.phone());
    }

    @Test
    public void list_WhenHavingRegisteredUsersWithRoleUser_ReturnsUsers() {
        when(userRepository.findAllUserRoleUser(Role.ROLE_USER)).thenReturn(LIST_USERS);
        when(userMapper.toDto(any(User.class))).thenAnswer(
                invocation -> {
                    User user = invocation.getArgument(0, User.class);
                    return ResponseUserDTO.builder()
                            .idUser(user.getId())
                            .email(user.getEmail())
                            .phone(user.getPhone())
                            .createdAt(user.getCreatedAt().toString())
                            .updatedAt(user.getUpdatedAt().toString())
                            .role(user.getRole())
                            .lastName(user.getLastName())
                            .firstName(user.getFirstName())
                            .isActive(user.isEnabled())
                            .build();
                }
        );

        List<ResponseUserDTO> sut = userService.list();

        assertThat(sut).isNotNull();
        assertThat(sut.size()).isEqualTo(LIST_USERS.size());
        assertThat(sut).extracting("idUser").containsExactly(1L, 2L, 3L);
        verify(userRepository).findAllUserRoleUser(Role.ROLE_USER);
    }

    @Test
    public void list_WhenNoRegisteredUser_ReturnsEmptyList() {
        when(userRepository.findAllUserRoleUser(Role.ROLE_USER)).thenReturn(List.of());

        List<ResponseUserDTO> sut = userService.list();

        assertThat(sut).isNotNull();
        assertThat(sut).isEmpty();
        verify(userRepository).findAllUserRoleUser(Role.ROLE_USER);
    }

    @Test
    public void getAllUserTraining_WithExisingUserAndRegisteredInfoTraining_ReturnsInfoTraining() {
        when(userRepository.existsUsersById(1L)).thenReturn(true);
        when(userRepository.getAllUserTrainingInfo(1L)).thenReturn(PERIODIZATION_INFO_LIST);

        List<UserPeriodizationInfoDTO> sut = userService.getAllUserTraining(1L);

        assertThat(sut).isNotNull();
        assertThat(sut.size()).isEqualTo(PERIODIZATION_INFO_LIST.size());
        assertThat(sut).isEqualTo(PERIODIZATION_INFO_LIST);
        verify(userRepository).existsUsersById(1L);
        verify(userRepository).getAllUserTrainingInfo(1L);
    }

    @Test
    public void getAllUserTraining_WhenThereNoinfoTrainingUser_ReturnsEmptyList() {
        when(userRepository.existsUsersById(1L)).thenReturn(true);
        when(userRepository.getAllUserTrainingInfo(1L)).thenReturn(List.of());

        List<UserPeriodizationInfoDTO> sut = userService.getAllUserTraining(1L);

        assertThat(sut).isNotNull();
        assertThat(sut).isEmpty();
        verify(userRepository).existsUsersById(1L);
        verify(userRepository).getAllUserTrainingInfo(1L);
    }

    @Test
    public void getAllUserTraining_WhenUnexistingUser_ThrowsException() {
        when(userRepository.existsUsersById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.getAllUserTraining(1L));
        verify(userRepository).existsUsersById(1L);
    }

}
