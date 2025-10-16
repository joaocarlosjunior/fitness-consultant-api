package br.com.fitnessconsultant.services;

import br.com.fitnessconsultant.domain.entities.MuscleGroup;
import br.com.fitnessconsultant.domain.repository.MuscleGroupRepository;
import br.com.fitnessconsultant.dto.musuculegroup.RequestMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.ResponseMuscleGroupDTO;
import br.com.fitnessconsultant.exception.ApiErrorException;
import br.com.fitnessconsultant.exception.InfoAlreadyExistsException;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.factories.MuscleGroupFactory;
import br.com.fitnessconsultant.mappers.MuscleGroupMapper;
import br.com.fitnessconsultant.service.musclegroup.impl.MuscleGroupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MuscleGroupServiceTest {
    @InjectMocks
    private MuscleGroupServiceImpl muscleGroupService;
    @Mock
    private MuscleGroupRepository muscleGroupRepository;
    @Mock
    private MuscleGroupMapper muscleGroupMapper;
    private MuscleGroup muscleGroup;
    private RequestMuscleGroupDTO muscleGroupRequest;
    private ResponseMuscleGroupDTO muscleGroupResponse;

    @BeforeEach
    void setUp() {
        muscleGroup = MuscleGroupFactory.createMuscleGroup();
        muscleGroupRequest = MuscleGroupFactory.createMuscleGroupRequest();
        muscleGroupResponse = MuscleGroupFactory.createMuscleGroupResponse();
    }

    @Test
    public void create_WithUnregisteredMuscleGroup_DoesNotThrowAnyException() {
        when(muscleGroupRepository.existsByNameIgnoreCase(muscleGroupRequest.name())).thenReturn(false);
        when(muscleGroupMapper.toEntity(muscleGroupRequest)).thenReturn(muscleGroup);

        assertThatCode(() -> muscleGroupService.create(muscleGroupRequest)).doesNotThrowAnyException();
        verify(muscleGroupRepository).existsByNameIgnoreCase(muscleGroupRequest.name());
        verify(muscleGroupMapper).toEntity(muscleGroupRequest);
        verify(muscleGroupRepository).save(muscleGroup);
    }

    @Test
    public void create_WithRegisteredMuscleGroup_ThrowsException() {
        when(muscleGroupRepository.existsByNameIgnoreCase(muscleGroupRequest.name())).thenReturn(true);

        assertThatCode(() -> muscleGroupService.create(muscleGroupRequest))
                .hasMessage("Grupo Muscular já cadastrado")
                .isInstanceOf(InfoAlreadyExistsException.class);
        verify(muscleGroupRepository).existsByNameIgnoreCase(muscleGroupRequest.name());
    }

    @Test
    public void findById_WithExistingId_ReturnsMuscleGroup() {
        when(muscleGroupRepository.findById(1L)).thenReturn(Optional.of(muscleGroup));
        when(muscleGroupMapper.toDto(muscleGroup)).thenReturn(muscleGroupResponse);

        ResponseMuscleGroupDTO sut = muscleGroupService.findById(1L);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(muscleGroupResponse);
        verify(muscleGroupRepository).findById(1L);
        verify(muscleGroupMapper).toDto(muscleGroup);
    }

    @Test
    public void findById_WithUnexistingId_ThrowsException() {
        when(muscleGroupRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatCode(() -> muscleGroupService.findById(99L))
                .hasMessage("Grupo Muscular não encontrado")
                .isInstanceOf(RecordNotFoundException.class);
        verify(muscleGroupRepository).findById(99L);
    }

    @Test
    public void delete_WithExistingId_DoesNotThrowAnyException() {
        when(muscleGroupRepository.findById(1L)).thenReturn(Optional.of(muscleGroup));

        assertThatCode(() -> muscleGroupService.delete(1L)).doesNotThrowAnyException();
        verify(muscleGroupRepository).findById(1L);
        verify(muscleGroupRepository).delete(muscleGroup);
    }

    @Test
    public void delete_WithUnexistingId_ThrowsException() {
        when(muscleGroupRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatCode(() -> muscleGroupService.delete(99L))
                .hasMessage("Grupo Muscular não encontrado")
                .isInstanceOf(RecordNotFoundException.class);
        verify(muscleGroupRepository).findById(99L);
    }

    @Test
    public void update_WithDataValidAndExistingId_ReturnsUpdatedMuscleGroup() {
        when(muscleGroupRepository.findById(1L)).thenReturn(Optional.of(muscleGroup));
        when(muscleGroupRepository.existsByNameIgnoreCase(muscleGroupRequest.name())).thenReturn(false);
        when(muscleGroupRepository.save(muscleGroup)).thenReturn(muscleGroup);
        when(muscleGroupMapper.toDto(muscleGroup)).thenReturn(muscleGroupResponse);

        ResponseMuscleGroupDTO sut = muscleGroupService.update(1L, muscleGroupRequest);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(muscleGroupResponse);
        verify(muscleGroupRepository).findById(1L);
        verify(muscleGroupRepository).save(muscleGroup);
        verify(muscleGroupMapper).toDto(muscleGroup);
    }

    @ParameterizedTest
    @MethodSource("providerInvalidMuscleGroupDTO")
    public void update_WithDataInvalidAndExistingId_ThrowsException(RequestMuscleGroupDTO requestMuscleGroupDTO) {
        when(muscleGroupRepository.findById(1L)).thenReturn(Optional.of(muscleGroup));

        assertThrows(ApiErrorException.class, () -> muscleGroupService.update(1L, requestMuscleGroupDTO));
    }

    @Test
    public void update_WithNameMuscleGroupAlreadyRegistered_ThrowsException() {
        when(muscleGroupRepository.findById(1L)).thenReturn(Optional.of(muscleGroup));
        when(muscleGroupRepository.existsByNameIgnoreCase(muscleGroupRequest.name())).thenReturn(true);

        assertThrows(InfoAlreadyExistsException.class, () -> muscleGroupService.update(1L, muscleGroupRequest));
        verify(muscleGroupRepository).findById(1L);
        verify(muscleGroupRepository).existsByNameIgnoreCase(muscleGroupRequest.name());
    }

    @Test
    public void update_WithUnexistingId_ThrowsException() {
        when(muscleGroupRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatCode(() -> muscleGroupService.update(99L, muscleGroupRequest))
                .hasMessage("Grupo Muscular não encontrado")
                .isInstanceOf(RecordNotFoundException.class);
        verify(muscleGroupRepository).findById(99L);
    }

    @Test
    public void list_WhenExistingMuscleGroups_ReturnsMuscleGroups() {
        List<MuscleGroup> muscleGroups = List.of(
                new MuscleGroup(1L, "Nome grupo muscular 1"),
                new MuscleGroup(2L, "Nome grupo muscular 2"),
                new MuscleGroup(3L, "Nome grupo muscular 3")
        );
        when(muscleGroupRepository.findAll()).thenReturn(muscleGroups);
        when(muscleGroupMapper.toDto(any(MuscleGroup.class))).thenAnswer(
                invocation -> {
                    MuscleGroup muscleGroup = invocation.getArgument(0);
                    return ResponseMuscleGroupDTO.builder()
                            .idMuscleGroup(muscleGroup.getId())
                            .name(muscleGroup.getName())
                            .build();
                }
        );

        List<ResponseMuscleGroupDTO> sut = muscleGroupService.list();

        assertThat(sut).isNotNull();
        assertThat(sut.size()).isEqualTo(muscleGroups.size());
        assertThat(sut).extracting("idMuscleGroup").containsExactly(1L, 2L, 3L);
        verify(muscleGroupRepository).findAll();
    }

    @Test
    public void list_WhenUnexistingMuscleGroups_ReturnsEmptyList() {
        when(muscleGroupRepository.findAll()).thenReturn(List.of());

        List<ResponseMuscleGroupDTO> sut = muscleGroupService.list();

        assertThat(sut).isNotNull();
        assertThat(sut).isEmpty();
        verify(muscleGroupRepository).findAll();
    }

    private static Stream<Arguments> providerInvalidMuscleGroupDTO() {
        return Stream.of(
                Arguments.of(new RequestMuscleGroupDTO(null)),
                Arguments.of(new RequestMuscleGroupDTO(""))
        );
    }
}
