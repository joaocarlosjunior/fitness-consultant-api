package br.com.fitnessconsultant.services;

import br.com.fitnessconsultant.domain.entities.ExerciseName;
import br.com.fitnessconsultant.domain.entities.MuscleGroup;
import br.com.fitnessconsultant.domain.repository.ExerciseNameRepository;
import br.com.fitnessconsultant.domain.repository.MuscleGroupRepository;
import br.com.fitnessconsultant.dto.exercisename.RequestExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.RequestUpdateExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.ResponseExerciseNameDTO;
import br.com.fitnessconsultant.exception.ApiErrorException;
import br.com.fitnessconsultant.exception.InfoAlreadyExistsException;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.factories.ExerciseNameFactory;
import br.com.fitnessconsultant.factories.MuscleGroupFactory;
import br.com.fitnessconsultant.mappers.ExerciseNameMapper;
import br.com.fitnessconsultant.service.exercisename.impl.ExerciseNameServiceImpl;
import jakarta.persistence.EntityNotFoundException;
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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseNameServiceTest {
    @InjectMocks
    private ExerciseNameServiceImpl exerciseNameService;
    @Mock
    private ExerciseNameRepository exerciseNameRepository;
    @Mock
    private MuscleGroupRepository muscleGroupRepository;
    @Mock
    private ExerciseNameMapper exerciseNameMapper;
    private ExerciseName exerciseName;
    private RequestExerciseNameDTO exerciseNameRequest;
    private ResponseExerciseNameDTO exerciseNameResponse;
    private RequestUpdateExerciseNameDTO updateExerciseNameRequest;
    private MuscleGroup muscleGroup;

    @BeforeEach
    void setUp() {
        exerciseName = ExerciseNameFactory.createExerciseName();
        exerciseNameRequest = ExerciseNameFactory.createExerciseNameRequest();
        exerciseNameResponse = ExerciseNameFactory.createExerciseNameResponse();
        updateExerciseNameRequest = ExerciseNameFactory.createExerciseNameUpdateRequest();
        muscleGroup = MuscleGroupFactory.createMuscleGroup();
    }

    @Test
    public void create_WithValidData_DoesNotThrowException() {
        when(exerciseNameRepository.existsByExerciseNameIgnoreCase(exerciseNameRequest.exerciseName())).thenReturn(false);
        when(muscleGroupRepository.getReferenceById(1L)).thenReturn(muscleGroup);
        when(exerciseNameMapper.toEntity(exerciseNameRequest, muscleGroup)).thenReturn(exerciseName);

        assertThatCode(() -> exerciseNameService.create(exerciseNameRequest)).doesNotThrowAnyException();
        verify(exerciseNameRepository).save(exerciseName);
        verify(exerciseNameRepository).existsByExerciseNameIgnoreCase(exerciseNameRequest.exerciseName());
        verify(muscleGroupRepository).getReferenceById(1L);
        verify(exerciseNameMapper).toEntity(exerciseNameRequest, muscleGroup);
    }

    @Test
    public void create_WhenExerciseNameAlreadyExists_ThrowsException() {
        when(exerciseNameRepository.existsByExerciseNameIgnoreCase(exerciseNameRequest.exerciseName())).thenReturn(true);

        assertThrows(InfoAlreadyExistsException.class, () -> exerciseNameService.create(exerciseNameRequest));
        verify(exerciseNameRepository, never()).save(any());
    }

    @Test
    public void create_WhenMuscleGroupNotFound_ThrowsException() {
        when(exerciseNameRepository.existsByExerciseNameIgnoreCase(exerciseNameRequest.exerciseName())).thenReturn(false);
        when(muscleGroupRepository.getReferenceById(exerciseNameRequest.idMuscleGroup())).thenThrow(new EntityNotFoundException());

        assertThatThrownBy(() -> exerciseNameService.create(exerciseNameRequest)).isInstanceOf(EntityNotFoundException.class);
        verify(exerciseNameRepository, never()).save(any());
    }

    @Test
    public void findById_WithExistingId_ReturnsExerciseName() {
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.of(exerciseName));
        when(exerciseNameMapper.toDto(exerciseName)).thenReturn(exerciseNameResponse);

        ResponseExerciseNameDTO sut  = exerciseNameService.findById(1L);

        assertThat(sut).isEqualTo(exerciseNameResponse);
        verify(exerciseNameRepository).findById(1L);
        verify(exerciseNameMapper).toDto(exerciseName);
    }

    @Test
    public void findById_WhenExerciseNameNotFound_ThrowsException() {
        when(exerciseNameRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseNameService.findById(99L)).isInstanceOf(RecordNotFoundException.class);
    }

    @Test
    public void update_WithValidData_ReturnsExerciseName(){
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.of(exerciseName));
        when(exerciseNameRepository.existsByExerciseNameIgnoreCase(updateExerciseNameRequest.exerciseName())).thenReturn(false);
        when(muscleGroupRepository.findById(updateExerciseNameRequest.idMuscleGroup())).thenReturn(Optional.of(muscleGroup));
        when(exerciseNameRepository.save(exerciseName)).thenReturn(exerciseName);
        when(exerciseNameMapper.toDto(exerciseName)).thenReturn(exerciseNameResponse);

        ResponseExerciseNameDTO sut = exerciseNameService.update(1L, updateExerciseNameRequest);

        assertThat(sut).isEqualTo(exerciseNameResponse);
        verify(exerciseNameRepository).findById(1L);
        verify(exerciseNameRepository).existsByExerciseNameIgnoreCase(updateExerciseNameRequest.exerciseName());
        verify(muscleGroupRepository).findById(updateExerciseNameRequest.idMuscleGroup());
        verify(exerciseNameMapper).toDto(exerciseName);
    }

    @Test
    public void update_WhenExerciseNameNotFound_ThrowsException() {
        when(exerciseNameRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> exerciseNameService.update(99L, updateExerciseNameRequest));
    }

    @ParameterizedTest
    @MethodSource("providerInvalidExerciseNames")
    public void update_WhenExerciseNameInvalid_ThrowsException(RequestUpdateExerciseNameDTO requestUpdateExerciseNameDTO) {
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.of(exerciseName));

        assertThrows(ApiErrorException.class, () -> exerciseNameService.update(1L, requestUpdateExerciseNameDTO));
        verify(exerciseNameRepository).findById(1L);
    }

    @Test
    public void update_WhenExerciseNameAlreadyExists_ThrowsException() {
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.of(exerciseName));
        when(exerciseNameRepository.existsByExerciseNameIgnoreCase(updateExerciseNameRequest.exerciseName())).thenReturn(true);

        assertThrows(InfoAlreadyExistsException.class, () -> exerciseNameService.update(1L, updateExerciseNameRequest));
        verify(exerciseNameRepository).findById(1L);
        verify(exerciseNameRepository).existsByExerciseNameIgnoreCase(updateExerciseNameRequest.exerciseName());
    }

    @Test
    public void update_WithUnexistingIDMuscleGroup_ThrowsException() {
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.of(exerciseName));
        when(exerciseNameRepository.existsByExerciseNameIgnoreCase(updateExerciseNameRequest.exerciseName())).thenReturn(false);
        when(muscleGroupRepository.findById(updateExerciseNameRequest.idMuscleGroup())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> exerciseNameService.update(1L, updateExerciseNameRequest));
        verify(exerciseNameRepository).findById(1L);
        verify(exerciseNameRepository).existsByExerciseNameIgnoreCase(updateExerciseNameRequest.exerciseName());
    }

    @Test
    public void delete_WithExistingID_NotThrowsException() {
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.of(exerciseName));

        assertThatCode(() -> exerciseNameService.delete(1L)).doesNotThrowAnyException();
        verify(exerciseNameRepository).findById(1L);
        verify(exerciseNameRepository).delete(exerciseName);
    }

    @Test
    public void delete_WithUnexistingID_ThrowsException() {
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> exerciseNameService.delete(1L));
        verify(exerciseNameRepository).findById(1L);
    }

    @Test
    public void list_WhenExistingExerciseNames_ReturnsExerciseNames() {
        List<ExerciseName> exerciseNames = List.of(
                new ExerciseName(1L, "Nome exercicio 1", muscleGroup),
                new ExerciseName(2L, "Nome exercicio 2", muscleGroup),
                new ExerciseName(3L, "Nome exercicio 3", muscleGroup)
        );
        when(exerciseNameRepository.findAll()).thenReturn(exerciseNames);
        when(exerciseNameMapper.toDto(any(ExerciseName.class)))
                .thenAnswer(invocation -> {
                    ExerciseName exerciseName = invocation.getArgument(0);
                    return ResponseExerciseNameDTO.builder()
                            .idExerciseName(exerciseName.getId())
                            .exerciseName(exerciseName.getExerciseName())
                            .muscleGroup(exerciseName.getMuscleGroup().getName())
                            .build();
                });

        List<ResponseExerciseNameDTO> sut = exerciseNameService.list();

        assertThat(sut.size()).isEqualTo(exerciseNames.size());
        assertThat(sut).extracting("idExerciseName").containsExactly(1L, 2L, 3L);
        verify(exerciseNameRepository).findAll();
    }

    @Test
    public void list_WithNoRegisteredEntity_ReturnsEmptyList() {
        when(exerciseNameRepository.findAll()).thenReturn(List.of());

        List<ResponseExerciseNameDTO> sut = exerciseNameService.list();

        assertThat(sut).isEmpty();
        verify(exerciseNameRepository).findAll();
    }

    private static Stream<Arguments> providerInvalidExerciseNames() {
        return Stream.of(
                Arguments.of(new RequestUpdateExerciseNameDTO("", 1L)),
                Arguments.of(new RequestUpdateExerciseNameDTO(null, 1L))
        );
    }

}
