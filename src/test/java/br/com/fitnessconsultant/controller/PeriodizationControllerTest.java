package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.exception.UserNotFoundException;
import br.com.fitnessconsultant.factories.PeriodizationFactory;
import br.com.fitnessconsultant.service.periodization.PeriodizationService;
import br.com.fitnessconsultant.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
    private RequestPeriodizationDTO periodizationRequest;
    private ResponsePeriodizationDTO periodizationResponse;
    private UpdatePeriodizationDTO updatePeriodizationRequest;
    private List<ResponsePeriodizationDTO> periodizationResponseList;

    @BeforeEach
    void setUp() {
        periodizationRequest = PeriodizationFactory.createPeriodizationRequest();
        periodizationResponse = PeriodizationFactory.createPeriodizationResponse();
        updatePeriodizationRequest = PeriodizationFactory.createUpdatePeriodization();
        periodizationResponseList = PeriodizationFactory.createPeriodizationListResponse();
    }

    @Test
    public void create_WithValidData_ReturnsPeriodization() throws Exception {
        when(periodizationService.create(periodizationRequest)).thenReturn(periodizationResponse);

        mockMvc.perform(post("/api/v1/periodizations")
                        .content(objectMapper.writeValueAsString(periodizationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(periodizationResponse)));
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
        when(periodizationService.findById(1L)).thenReturn(periodizationResponse);

        mockMvc.perform(get("/api/v1/periodizations/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(periodizationResponse)));
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
        when(periodizationService.update(1L, updatePeriodizationRequest)).thenReturn(periodizationResponse);

        mockMvc.perform(put("/api/v1/periodizations/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePeriodizationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(periodizationResponse)));
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
        doThrow(RecordNotFoundException.class).when(periodizationService).update(99L, updatePeriodizationRequest);

        mockMvc.perform(put("/api/v1/periodizations/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePeriodizationRequest)))
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
        when(periodizationService.list()).thenReturn(periodizationResponseList);

        mockMvc.perform(get("/api/v1/periodizations"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(periodizationResponseList)));
    }

    @Test
    public void list_IfNotThereRegistered_ReturnsEmptyList() throws Exception {
        when(periodizationService.list()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/periodizations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void getAllPeriodizationByIdUser_WithExistingUser_ReturnsPeriodizations() throws Exception {
        when(periodizationService.getAllPeriodizationByUser(1L)).thenReturn(periodizationResponseList);

        mockMvc.perform(get("/api/v1/periodizations/user/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(periodizationResponseList)));
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
