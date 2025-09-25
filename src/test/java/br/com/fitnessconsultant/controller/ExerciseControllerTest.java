package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.exercise.RequestExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.ResponseExerciseDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.service.exercise.ExerciseService;
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

import java.util.stream.Stream;

import static br.com.fitnessconsultant.common.ExerciseConstants.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExerciseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ExerciseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private JwtTokenUtils jwtTokenUtils;
    @MockitoBean
    private ExerciseService exerciseService;

    @Test
    public void create_WithValidData_ReturnsExcercise() throws Exception {
        when(exerciseService.create(EXERCISE_REQUEST)).thenReturn(EXERCISE_RESPONSE);

        mockMvc.perform(post("/api/v1/exercises")
                        .content(objectMapper.writeValueAsString(EXERCISE_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idExercise").value(EXERCISE_RESPONSE.getIdExercise()))
                .andExpect(jsonPath("$.series").value(EXERCISE_RESPONSE.getSeries()))
                .andExpect(jsonPath("$.repetitions").value(EXERCISE_RESPONSE.getRepetitions()))
                .andExpect(jsonPath("$.initialLoad").value(EXERCISE_RESPONSE.getInitialLoad()))
                .andExpect(jsonPath("$.finalLoad").value(EXERCISE_RESPONSE.getFinalLoad()))
                .andExpect(jsonPath("$.method").value(EXERCISE_RESPONSE.getMethod()))
                .andExpect(jsonPath("$.exerciseName").value(EXERCISE_RESPONSE.getExerciseName()));
    }

    @ParameterizedTest
    @MethodSource("providersInvalidsExercises")
    public void create_WithInvalidData_ReturnsError(RequestExerciseDTO requestExerciseDTO) throws Exception {
        mockMvc.perform(post("/api/v1/exercises")
                        .content(objectMapper.writeValueAsString(requestExerciseDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithValidData_ReturnsExcercise() throws Exception {
        RequestExerciseDTO updateExercise = new RequestExerciseDTO(1L, 4, "Atualizado Exercicio", 20, 35, "metodo teste", 1L);
        ResponseExerciseDTO responseExercise = new ResponseExerciseDTO(1L, 4, "Atualizado Exercicio", 20, 35, "metodo teste", "Nome exercicio");

        when(exerciseService.update(1L, updateExercise)).thenReturn(responseExercise);

        mockMvc.perform(put("/api/v1/exercises/{id}", 1L)
                        .content(objectMapper.writeValueAsString(updateExercise))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseExercise)));
    }

    @Test
    public void update_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(new RecordNotFoundException("Exercício não encontrado")).when(exerciseService).update(1L, EXERCISE_REQUEST);

        mockMvc.perform(put("/api/v1/exercises/{id}", 1L)
                        .content(objectMapper.writeValueAsString(EXERCISE_REQUEST))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(put("/api/v1/exercises/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("providersInvalidsExercises")
    public void update_WithInvalidData_ReturnsBadRequest(RequestExerciseDTO requestExerciseDTO) throws Exception {
        mockMvc.perform(put("/api/v1/exercises/{id}", 1L)
                        .content(objectMapper.writeValueAsString(requestExerciseDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/exercises/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_WithExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/exercises/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void delete_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(new RecordNotFoundException("Exercício não encontrado")).when(exerciseService).delete(1L);

        mockMvc.perform(delete("/api/v1/exercises/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(delete("/api/v1/exercises/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void delete_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/exercises/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllExercisesByIdTraining_WithExistingId_ReturnsExercises() throws Exception {
        when(exerciseService.getAllExercisesByIdTraining(1L)).thenReturn(LIST_EXERCISES);

        mockMvc.perform(get("/api/v1/exercises/training/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(LIST_EXERCISES)));
    }

    @Test
    public void getAllExercisesByIdTraining_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(RecordNotFoundException.class).when(exerciseService).getAllExercisesByIdTraining(1L);

        mockMvc.perform(get("/api/v1/exercises/training/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllExercisesByIdTraining_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/exercises/training/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllExercisesByIdTraining_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/exercises/training/"))
                .andExpect(status().isNotFound());
    }

    private static Stream<Arguments> providersInvalidsExercises() {
        return Stream.of(
                Arguments.of(new RequestExerciseDTO(null, 2, "Repeticoes teste", 20, 35, "Metodo teste", 1L)),
                Arguments.of(new RequestExerciseDTO(1L, 2, "Repeticoes teste", 20, 35, "Metodo teste", null)),
                Arguments.of(new RequestExerciseDTO(-1L, 2, "Repeticoes teste", 20, 35, "Metodo teste", 1L)),
                Arguments.of(new RequestExerciseDTO(1L, -2, "Repeticoes teste", 20, 35, "Metodo teste", 1L)),
                Arguments.of(new RequestExerciseDTO(1L, 2, "Repeticoes teste", -20, 35, "Metodo teste", 1L)),
                Arguments.of(new RequestExerciseDTO(1L, 2, "Repeticoes teste", 20, -35, "Metodo teste", 1L)),
                Arguments.of(new RequestExerciseDTO(1L, 2, "Repeticoes teste", 20, 35, "Metodo teste", -1L)),
                Arguments.of(new RequestExerciseDTO(null, null, null, null, null, null, null))
        );
    }
}
