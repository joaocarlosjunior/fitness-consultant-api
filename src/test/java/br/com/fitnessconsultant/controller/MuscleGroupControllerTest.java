package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.musuculegroup.RequestMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.ResponseMuscleGroupDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.factories.MuscleGroupFactory;
import br.com.fitnessconsultant.service.musclegroup.MuscleGroupService;
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

@WebMvcTest(MuscleGroupController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MuscleGroupControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private JwtTokenUtils jwtTokenUtils;
    @MockitoBean
    private MuscleGroupService muscleGroupService;
    private RequestMuscleGroupDTO muscleGroupRequest;
    private ResponseMuscleGroupDTO muscleGroupResponse;
    private List<ResponseMuscleGroupDTO> muscleGroupResponseList;

    @BeforeEach
    void setUp() {
        muscleGroupRequest = MuscleGroupFactory.createMuscleGroupRequest();
        muscleGroupResponse = MuscleGroupFactory.createMuscleGroupResponse();
        muscleGroupResponseList = MuscleGroupFactory.createMuscleGroupList();
    }

    @Test
    public void create_WithValidData_ReturnsCreated() throws Exception {
        mockMvc.perform(post("/api/v1/muscle-groups")
                .content(objectMapper.writeValueAsString(muscleGroupRequest))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("providersInvalidMuscleGroup")
    public void create_WithInvalidData_ReturnsBadRequest(RequestMuscleGroupDTO requestMuscleGroupDTO) throws Exception {
        mockMvc.perform(post("/api/v1/muscle-groups")
                .content(objectMapper.writeValueAsString(requestMuscleGroupDTO))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void delete_WithExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/muscle-groups/{id}", 1L)
        ).andExpect(status().isNoContent());
    }

    @Test
    public void delete_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(RecordNotFoundException.class).when(muscleGroupService).delete(1L);

        mockMvc.perform(delete("/api/v1/muscle-groups/{id}", 1L)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void delete_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(delete("/api/v1/muscle-groups/{id}", -1L)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void delete_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/muscle-groups/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_WithValidData_ReturnsMuscleGroup() throws Exception {
        when(muscleGroupService.update(1L, muscleGroupRequest)).thenReturn(muscleGroupResponse);

        mockMvc.perform(put("/api/v1/muscle-groups/{id}", 1L)
                        .content(objectMapper.writeValueAsString(muscleGroupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(muscleGroupResponse)));
    }

    @ParameterizedTest
    @MethodSource("providersInvalidMuscleGroup")
    public void update_WithInvalidData_ReturnsBadRequest(RequestMuscleGroupDTO requestMuscleGroupDTO) throws Exception {
        mockMvc.perform(put("/api/v1/muscle-groups/{id}", 1L)
                        .content(objectMapper.writeValueAsString(requestMuscleGroupDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(RecordNotFoundException.class).when(muscleGroupService).update(1L, muscleGroupRequest);

        mockMvc.perform(put("/api/v1/muscle-groups/{id}", 1L)
                        .content(objectMapper.writeValueAsString(muscleGroupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void update_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(put("/api/v1/muscle-groups/{id}", -1L)
                        .content(objectMapper.writeValueAsString(muscleGroupRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(put("/api/v1/muscle-groups/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_WithExistingId_ReturnsMuscleGroup() throws Exception {
        when(muscleGroupService.findById(1L)).thenReturn(muscleGroupResponse);

        mockMvc.perform(get("/api/v1/muscle-groups/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(muscleGroupResponse)));
    }

    @Test
    public void findById_WithUnexistingId_ReturnsNotFound() throws Exception {
        doThrow(RecordNotFoundException.class).when(muscleGroupService).findById(1L);

        mockMvc.perform(get("/api/v1/muscle-groups/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_WithInvalidId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/muscle-groups/{id}", -1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findById_WithoutIdInPath_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/muscle-groups/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void list_IfThereRegistered_ReturnsMuscleGroups() throws Exception {
        when(muscleGroupService.list()).thenReturn(muscleGroupResponseList);

        mockMvc.perform(get("/api/v1/muscle-groups"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(muscleGroupResponseList)));
    }

    @Test
    public void list_IfNotThereRegistered_ReturnsEmptyList() throws Exception {
        when(muscleGroupService.list()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/muscle-groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    private static Stream<Arguments> providersInvalidMuscleGroup() {
        return Stream.of(
                Arguments.of(new RequestMuscleGroupDTO("")),
                Arguments.of(new RequestMuscleGroupDTO(null)),
                Arguments.of(new RequestMuscleGroupDTO("ABCDEFGHIJKLMNOPQRSTUVWXYZabcddefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuv"))
        );
    }
}
