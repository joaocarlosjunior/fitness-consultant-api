package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.exception.UserNotFoundException;
import br.com.fitnessconsultant.service.periodization.PeriodizationService;
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

import java.util.List;
import java.util.stream.Stream;

import static br.com.fitnessconsultant.common.PeriodizationConstants.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PeriodizationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PeriodizationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private JwtTokenUtils jwtTokenUtils;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private PeriodizationService periodizationService;

    @Test
    public void create_WithValidData_ReturnsPeriodization() throws Exception {
        when(periodizationService.create(PERIODIZATION)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(PERIODIZATION_RESPONSE));

        mockMvc.perform(post("/api/v1/periodizations")
                        .content(objectMapper.writeValueAsString(PERIODIZATION))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(PERIODIZATION_RESPONSE)));
    }

    @ParameterizedTest
    @MethodSource("providersInvalidPeriodizations")
    public void create_WithInvalidData_ReturnsBadRequest(RequestPeriodizationDTO requestPeriodizationDTO) throws Exception {
        mockMvc.perform(post("/api/v1/periodizations")
                        .content(objectMapper.writeValueAsString(requestPeriodizationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findById_WithExistingId_ReturnsPeriodization() throws Exception {
        when(periodizationService.findById(1L)).thenReturn(ResponseEntity.ok(PERIODIZATION_RESPONSE));

        mockMvc.perform(get("/api/v1/periodizations/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(PERIODIZATION_RESPONSE)));
    }

    @Test
    public void findById_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(RecordNotFoundException.class).when(periodizationService).findById(1L);

        mockMvc.perform(get("/api/v1/periodizations/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/periodizations/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findById_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/periodizations/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_WithValidData_ReturnsPeriodization() throws Exception {
        UpdatePeriodizationDTO updatePeriodizationDTO = new UpdatePeriodizationDTO("Nome Periodizacao Atualizado", 10, 1L);
        ResponsePeriodizationDTO responsePeriodizationDTO = new ResponsePeriodizationDTO(1L, "Nome Periodizacao Atualizado", 10, "2025-09-16T15:42:30", "2025-09-16T15:42:30", "2025-09-16T15:42:30");
        when(periodizationService.update(1L, updatePeriodizationDTO)).thenReturn(ResponseEntity.ok(responsePeriodizationDTO));

        mockMvc.perform(put("/api/v1/periodizations/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePeriodizationDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responsePeriodizationDTO)));
    }

    @ParameterizedTest
    @MethodSource("providersInvalidUpdatePeriodizations")
    public void update_WithInvalidData_ReturnsBadRequest(UpdatePeriodizationDTO updatePeriodizationDTO) throws Exception {
        mockMvc.perform(put("/api/v1/periodizations/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePeriodizationDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithUnexistingPeriodization_ReturnsNotFound() throws Exception {
        UpdatePeriodizationDTO updatePeriodizationDTO = new UpdatePeriodizationDTO("Nome Periodizacao Atualizado", 10, 1L);
        doThrow(RecordNotFoundException.class).when(periodizationService).update( 99L,updatePeriodizationDTO);

        mockMvc.perform(put("/api/v1/periodizations/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePeriodizationDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(put("/api/v1/periodizations/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/periodizations/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_WithExistingPeriodization_ReturnsNotContent() throws Exception {
        mockMvc.perform(delete("/api/v1/periodizations/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void delete_WithUnexistingPeriodization_ReturnsNotFound() throws Exception {
        doThrow(RecordNotFoundException.class).when(periodizationService).delete(1L);

        mockMvc.perform(delete("/api/v1/periodizations/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(delete("/api/v1/periodizations/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void delete_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/periodizations/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void list_IfThereRegistered_ReturnsPeriodizations() throws Exception {
        when(periodizationService.list()).thenReturn(ResponseEntity.ok(PERIODIZATION_LIST));

        mockMvc.perform(get("/api/v1/periodizations"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(PERIODIZATION_LIST)));
    }

    @Test
    public void list_IfNotThereRegistered_ReturnsEmptyList() throws Exception {
        when(periodizationService.list()).thenReturn(ResponseEntity.ok(List.of()));

        mockMvc.perform(get("/api/v1/periodizations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void getAllPeriodizationByIdUser_WithExistingUser_ReturnsPeriodizations() throws Exception {
        when(periodizationService.getAllPeriodizationByUser(1L)).thenReturn(ResponseEntity.ok(PERIODIZATION_LIST));

        mockMvc.perform(get("/api/v1/periodizations/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(PERIODIZATION_LIST)));
    }

    @Test
    public void getAllPeriodizationByIdUser_WithUnexistingUser_ReturnsNotFound() throws Exception {
        doThrow(UserNotFoundException.class).when(periodizationService).getAllPeriodizationByUser(1L);

        mockMvc.perform(get("/api/v1/periodizations/user/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllPeriodizationByIdUser_WithIdInvalid_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/periodizations/user/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllPeriodizationByIdUser_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/periodizations/user/"))
                .andExpect(status().isNotFound());
    }

    private static Stream<Arguments> providersInvalidUpdatePeriodizations() {
        return Stream.of(
                Arguments.of(new UpdatePeriodizationDTO("Nome Periodizacao", -1, 1L)),
                Arguments.of(new UpdatePeriodizationDTO("Nome Periodizacao", 1, -1L)),
                Arguments.of(new UpdatePeriodizationDTO("ABCDEFGHIJKLMNOPQRSTUVWXYZabBcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuv", 1, 1L))
        );
    }

    private static Stream<Arguments> providersInvalidPeriodizations() {
        return Stream.of(
                Arguments.of(new RequestPeriodizationDTO("", 1, 1L)),
                Arguments.of(new RequestPeriodizationDTO("Nome Periodizacao", -1, 1L)),
                Arguments.of(new RequestPeriodizationDTO("Nome Periodizacao", 1, -1L)),
                Arguments.of(new RequestPeriodizationDTO(null, 1, 1L)),
                Arguments.of(new RequestPeriodizationDTO("Nome Periodizacao", null, 1L)),
                Arguments.of(new RequestPeriodizationDTO("Nome Periodizacao", 1, null)),
                Arguments.of(new RequestPeriodizationDTO("ABCDEFGHIJKLMNOPQRSTUVWXYZabBcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuv", 1, 1L)),
                Arguments.of(new RequestPeriodizationDTO(null, null, null))
        );
    }
}
