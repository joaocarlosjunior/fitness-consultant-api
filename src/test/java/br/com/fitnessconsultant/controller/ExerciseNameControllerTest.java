package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.exercisename.RequestExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.RequestUpdateExerciseNameDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.service.exercisename.ExerciseNameService;
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

import java.util.List;
import java.util.stream.Stream;

import static br.com.fitnessconsultant.common.ExerciseNameConstants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExerciseNameController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExerciseNameControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private JwtTokenUtils jwtTokenUtils;
    @MockitoBean
    private ExerciseNameService exerciseNameService;

    @Test
    public void create_WithValidData_ReturnsCreated() throws Exception {
        doNothing().when(exerciseNameService).create(EXERCISE_NAME);

        mockMvc.perform(post("/api/v1/exercise-name")
                        .content(objectMapper.writeValueAsString(EXERCISE_NAME))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("providersInvalidExerciseNames")
    public void create_WithInvalidData_ReturnsBadRequest(RequestExerciseNameDTO invalidExerciseName) throws Exception {
        doNothing().when(exerciseNameService).create(invalidExerciseName);

        mockMvc.perform(post("/api/v1/exercise-name")
                        .content(objectMapper.writeValueAsString(invalidExerciseName))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findById_WithExistingId_ReturnsExerciseName() throws Exception {
        when(exerciseNameService.findById(EXERCISE_NAME_RESPONSE.getIdExerciseName())).thenReturn(EXERCISE_NAME_RESPONSE);

        mockMvc.perform(get("/api/v1/exercise-name/{id}", EXERCISE_NAME_RESPONSE.getIdExerciseName()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(EXERCISE_NAME_RESPONSE)));
    }

    @Test
    public void findById_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(RecordNotFoundException.class).when(exerciseNameService).findById(99L);

        mockMvc.perform(get("/api/v1/exercise-name/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/exercise-name/{id}", -99L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findById_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/exercise-name/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_WithValidData_ReturnsExerciseName() throws Exception {
        RequestUpdateExerciseNameDTO updateExerciseNameDTO = new RequestUpdateExerciseNameDTO("Nome Exercicio", 1L);
        when(exerciseNameService.update(1L, updateExerciseNameDTO)).thenReturn(EXERCISE_NAME_RESPONSE);

        mockMvc.perform(put("/api/v1/exercise-name/{id}", 1L)
                        .content(objectMapper.writeValueAsString(updateExerciseNameDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(EXERCISE_NAME_RESPONSE)));
    }

    @ParameterizedTest
    @MethodSource("providersInvalidUpdateExerciseNames")
    public void update_WithInvalidData_ReturnsBadRequest(RequestUpdateExerciseNameDTO requestUpdateExerciseNameDTO) throws Exception {
        mockMvc.perform(put("/api/v1/exercise-name/{id}", 1L)
                        .content(objectMapper.writeValueAsString(requestUpdateExerciseNameDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithUnexistingId_ReturnsNotFound() throws Exception {
        RequestUpdateExerciseNameDTO updateExerciseNameDTO = new RequestUpdateExerciseNameDTO("Nome Exercicio", 1L);
        doThrow(RecordNotFoundException.class).when(exerciseNameService).update(1L, updateExerciseNameDTO);

        mockMvc.perform(put("/api/v1/exercise-name/{id}", 1L)
                        .content(objectMapper.writeValueAsString(updateExerciseNameDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_WithInvalidId_ReturnsBadRequest() throws Exception {
        RequestUpdateExerciseNameDTO updateExerciseNameDTO = new RequestUpdateExerciseNameDTO("Nome Exercicio", 1L);

        mockMvc.perform(put("/api/v1/exercise-name/{id}", -1L)
                        .content(objectMapper.writeValueAsString(updateExerciseNameDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/exercise-name/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_WithExistingId_ReturnsNoContent() throws Exception {
        doNothing().when(exerciseNameService).delete(1L);

        mockMvc.perform(delete("/api/v1/exercise-name/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void delete_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(RecordNotFoundException.class).when(exerciseNameService).delete(99L);

        mockMvc.perform(delete("/api/v1/exercise-name/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(delete("/api/v1/exercise-name/{id}", -99L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void delete_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/exercise-name/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void list_IfThereRegistered_ReturnsExerciseNames() throws Exception {
        when(exerciseNameService.list()).thenReturn(EXERCISE_NAME_LIST);

        mockMvc.perform(get("/api/v1/exercise-name"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(EXERCISE_NAME_LIST)));
    }

    @Test
    public void list_IfNotThereRegistered_ReturnsEmptyList() throws Exception {
        when(exerciseNameService.list()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/exercise-name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    private static Stream<Arguments> providersInvalidUpdateExerciseNames() {
        return Stream.of(

                Arguments.of(new RequestUpdateExerciseNameDTO("", 1L)),
                Arguments.of(new RequestUpdateExerciseNameDTO("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghsijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuv", 1L)),
                Arguments.of(new RequestUpdateExerciseNameDTO("Teste", -1L))
        );
    }

    private static Stream<Arguments> providersInvalidExerciseNames() {
        return Stream.of(
                Arguments.of(new RequestExerciseNameDTO("", 1L)),
                Arguments.of(new RequestExerciseNameDTO("Nome de Exercicio", -1L)),
                Arguments.of(new RequestExerciseNameDTO(null, null))
        );
    }
}
