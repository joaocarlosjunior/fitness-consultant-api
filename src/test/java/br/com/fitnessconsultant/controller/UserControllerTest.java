package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;
import br.com.fitnessconsultant.exception.UserNotFoundException;
import br.com.fitnessconsultant.service.user.UserService;
import br.com.fitnessconsultant.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static br.com.fitnessconsultant.common.UserConstants.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private JwtTokenUtils jwtTokenUtils;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private UserService userService;

    @Test
    public void findById_WithExistingId_ReturnsUser() throws Exception {
        when(userService.findById(1L)).thenReturn(USER_RESPONSE);

        mockMvc.perform(get("/api/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(USER_RESPONSE)));
    }

    @Test
    public void findById_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(UserNotFoundException.class).when(userService).findById(1L);

        mockMvc.perform(get("/api/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/users/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_WithExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void delete_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(UserNotFoundException.class).when(userService).setDisableUser(99L);

        mockMvc.perform(delete("/api/v1/users/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void delete_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/users/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_WithValidData_ReturnsUser() throws Exception {
        when(userService.update(1L, UPDATE_USER)).thenReturn(USER_RESPONSE);

        mockMvc.perform(put("/api/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_USER)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(USER_RESPONSE)));
    }

    @ParameterizedTest
    @MethodSource("providersInvalidUpdateUsers")
    public void update_WithInvalidData_ReturnsBadRequest(UpdateUserDTO updateUserDTO) throws Exception {
        mockMvc.perform(put("/api/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithUnexistingId_ReturnsBadRequest() throws Exception {
        doThrow(UserNotFoundException.class).when(userService).update(99L, UPDATE_USER);

        mockMvc.perform(put("/api/v1/users/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_USER)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(put("/api/v1/users/{id}", -1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UPDATE_USER)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/users/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void list_HaveRegistered_ReturnsUsers() throws Exception {
        when(userService.list()).thenReturn(USERS);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(USERS)));
    }

    @Test
    public void list_NoHaveRegistered_ReturnsEmptyList() throws Exception {
        when(userService.list()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of())));
    }

    @Test
    public void setActiveUser_WithExistingId_ReturnsOkAndMessage() throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário ativado com sucesso");

        when(userService.setActiveUser(1L)).thenReturn(true);

        mockMvc.perform(patch("/api/v1/users/active-user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void setActiveUser_WithUnexistingId_ReturnsOkAndMessage() throws Exception {
        doThrow(UserNotFoundException.class).when(userService).setActiveUser(99L);

        mockMvc.perform(patch("/api/v1/users/active-user/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void setActiveUser_WithAlreadyActiveUser_ReturnsConflictAndMessage() throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário já ativo");

        when(userService.setActiveUser(1L)).thenReturn(false);

        mockMvc.perform(patch("/api/v1/users/active-user/{id}", 1L))
                .andExpect(status().isConflict())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void setActiveUser_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(patch("/api/v1/users/active-user/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void setActiveUser_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(patch("/api/v1/users/active-user/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void setDisableUser_WithExistingId_ReturnsOkAndMessage() throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário desativado com sucesso");

        when(userService.setDisableUser(1L)).thenReturn(true);

        mockMvc.perform(patch("/api/v1/users/disable-user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void setDisableUser_WithUnexistingId_ReturnsOkAndMessage() throws Exception {
        doThrow(UserNotFoundException.class).when(userService).setDisableUser(99L);

        mockMvc.perform(patch("/api/v1/users/disable-user/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void setDisableUser_WithAlreadyActiveUser_ReturnsConflictAndMessage() throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário já não está ativo");

        when(userService.setDisableUser(1L)).thenReturn(false);

        mockMvc.perform(patch("/api/v1/users/disable-user/{id}", 1L))
                .andExpect(status().isConflict())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void setDisableUser_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(patch("/api/v1/users/disable-user/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void setDisableUser_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(patch("/api/v1/users/disable-user/"))
                .andExpect(status().isNotFound());
    }

    private static Stream<Arguments> providersInvalidUpdateUsers(){
        return Stream.of(
                Arguments.of(new UpdateUserDTO("No", "Sobrenome Atualizado", "email@email.com", "77999999999")),
                Arguments.of(new UpdateUserDTO("Nome Atualizado", "So", "email@email.com", "77999999999")),
                Arguments.of(new UpdateUserDTO("Nome Atualizado", "Sobrenome Atualizado", "emailemail", "77999999999")),
                Arguments.of(new UpdateUserDTO("Nome Atualizado", "Sobrenome Atualizado", "email@email.com", "779999"))
        );
    }

}
