package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.training.RequestTrainingDTO;
import br.com.fitnessconsultant.dto.training.RequestUpdateTrainingDTO;
import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.service.training.TrainingService;
import br.com.fitnessconsultant.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static br.com.fitnessconsultant.common.TrainingConstants.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrainingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private JwtTokenUtils jwtTokenUtils;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private TrainingService trainingService;

    @Test
    public void create_WithValidData_ReturnsTraining() throws Exception {
        when(trainingService.create(TRAINING)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(RESPONSE_TRAINING));

        mockMvc.perform(post("/api/v1/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(TRAINING))
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(RESPONSE_TRAINING)));
    }

    @ParameterizedTest
    @MethodSource("providersInvalidTrainings")
    public void create_WithInvalidData_ReturnsBadRequest(RequestTrainingDTO requestTrainingDTO) throws Exception {
        mockMvc.perform(post("/api/v1/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestTrainingDTO))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithValidData_ReturnsUpdatedTraining() throws Exception {
        RequestUpdateTrainingDTO requestUpdateTrainingDTO = new RequestUpdateTrainingDTO(1L, 1, "Nome treino atualizado");
        ResponseTrainingDTO responseTrainingDTO = new ResponseTrainingDTO(1L, 1L, "A", "Nome treino atualizado", "2025-09-16T15:42:30", "2025-09-16T15:42:30");
        when(trainingService.update(1L, requestUpdateTrainingDTO)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(responseTrainingDTO));

        mockMvc.perform(put("/api/v1/workouts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUpdateTrainingDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseTrainingDTO)));
    }

    @Test
    public void update_WithUnexistingId_ReturnsNotFound() throws Exception {
        RequestUpdateTrainingDTO requestUpdateTrainingDTO = new RequestUpdateTrainingDTO(1L, 1, "Nome treino atualizado");
        doThrow(RecordNotFoundException.class).when(trainingService).update(99L, requestUpdateTrainingDTO);

        mockMvc.perform(put("/api/v1/workouts/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUpdateTrainingDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_WithInvalidId_ReturnsBadRequest() throws Exception {
        RequestUpdateTrainingDTO requestUpdateTrainingDTO = new RequestUpdateTrainingDTO(1L, 1, "Nome treino atualizado");

        mockMvc.perform(put("/api/v1/workouts/{id}", -1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUpdateTrainingDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/workouts/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_WithExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/workouts/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void delete_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(RecordNotFoundException.class).when(trainingService).delete(99L);

        mockMvc.perform(delete("/api/v1/workouts/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(delete("/api/v1/workouts/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void delete_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/workouts/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_WithExistingId_ReturnsTraining() throws Exception {
        when(trainingService.findById(1L)).thenReturn(ResponseEntity.ok(RESPONSE_TRAINING));

        mockMvc.perform(get("/api/v1/workouts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(RESPONSE_TRAINING)));
    }

    @Test
    public void findById_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(RecordNotFoundException.class).when(trainingService).findById(99L);

        mockMvc.perform(get("/api/v1/workouts/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/workouts/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findById_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/workouts/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllTrainingByIdPeriodization_WithExistingId_ReturnsTrainings() throws Exception {
        when(trainingService.getAllTrainingByIdPeriodization(1L)).thenReturn(ResponseEntity.ok(RESPONSE_TRAININGS));

        mockMvc.perform(get("/api/v1/workouts/periodization/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(RESPONSE_TRAININGS)));
    }

    @Test
    public void getAllTrainingByIdPeriodization_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(RecordNotFoundException.class).when(trainingService).getAllTrainingByIdPeriodization(99L);

        mockMvc.perform(get("/api/v1/workouts/periodization/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllTrainingByIdPeriodization_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/workouts/periodization/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllTrainingByIdPeriodization_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/workouts/periodization/"))
                .andExpect(status().isNotFound());
    }

    private static Stream<Arguments> providersInvalidTrainings() {
        return Stream.of(
                Arguments.of(new RequestTrainingDTO(null, 1, "Nome do treino")),
                Arguments.of(new RequestTrainingDTO(0L, 1, "Nome do treino")),
                Arguments.of(new RequestTrainingDTO(1L, null, "Nome do treino")),
                Arguments.of(new RequestTrainingDTO(1L, 1, null)),
                Arguments.of(new RequestTrainingDTO(1L, 1, "")),
                Arguments.of(new RequestTrainingDTO(-1L, 1, "Nome do treino")),
                Arguments.of(new RequestTrainingDTO(1L, -1, "Nome do treino")),
                Arguments.of(new RequestTrainingDTO(1L, 1, "ABCDEFGHIJKLMNOPQRSTUVWXYZabBcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuv"))
        );
    }
}
