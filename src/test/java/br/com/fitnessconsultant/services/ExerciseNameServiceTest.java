package br.com.fitnessconsultant.services;

import br.com.fitnessconsultant.domain.entities.ExerciseName;
import br.com.fitnessconsultant.domain.repository.ExerciseNameRepository;
import br.com.fitnessconsultant.domain.repository.MuscleGroupRepository;
import br.com.fitnessconsultant.dto.exercisename.RequestUpdateExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.ResponseExerciseNameDTO;
import br.com.fitnessconsultant.exception.ApiErrorException;
import br.com.fitnessconsultant.exception.InfoAlreadyExistsException;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.mappers.ExerciseNameMapper;
import br.com.fitnessconsultant.service.exercisename.impl.ExerciseNameServiceImpl;
import jakarta.persistence.EntityNotFoundException;
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

import static br.com.fitnessconsultant.common.ExerciseNameConstants.*;
import static br.com.fitnessconsultant.common.MusculeGroupConstants.MUSCLE_GROUP;
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

    @Test
    public void create_WithValidData_DoesNotThrowException() {
        when(exerciseNameRepository.existsByExerciseNameIgnoreCase(EXERCISE_NAME_REQUEST.exerciseName())).thenReturn(false);
        when(muscleGroupRepository.getReferenceById(1L)).thenReturn(MUSCLE_GROUP);
        when(exerciseNameMapper.toEntity(EXERCISE_NAME_REQUEST, MUSCLE_GROUP)).thenReturn(EXERCISE_NAME);

        assertThatCode(() -> exerciseNameService.create(EXERCISE_NAME_REQUEST)).doesNotThrowAnyException();
        verify(exerciseNameRepository).save(EXERCISE_NAME);
        verify(exerciseNameRepository).existsByExerciseNameIgnoreCase(EXERCISE_NAME_REQUEST.exerciseName());
        verify(muscleGroupRepository).getReferenceById(1L);
        verify(exerciseNameMapper).toEntity(EXERCISE_NAME_REQUEST, MUSCLE_GROUP);
    }

    @Test
    public void create_WhenExerciseNameAlreadyExists_ThrowsException() {
        when(exerciseNameRepository.existsByExerciseNameIgnoreCase(EXERCISE_NAME_REQUEST.exerciseName())).thenReturn(true);

        assertThrows(InfoAlreadyExistsException.class, () -> exerciseNameService.create(EXERCISE_NAME_REQUEST));
        verify(exerciseNameRepository, never()).save(any());
    }

    @Test
    public void create_WhenMuscleGroupNotFound_ThrowsException() {
        when(exerciseNameRepository.existsByExerciseNameIgnoreCase(EXERCISE_NAME_REQUEST.exerciseName())).thenReturn(false);
        when(muscleGroupRepository.getReferenceById(EXERCISE_NAME_REQUEST.idMuscleGroup())).thenThrow(new EntityNotFoundException());

        assertThatThrownBy(() -> exerciseNameService.create(EXERCISE_NAME_REQUEST)).isInstanceOf(EntityNotFoundException.class);
        verify(exerciseNameRepository, never()).save(any());
    }

    @Test
    public void findById_WithExistingId_ReturnsExerciseName() {
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.of(EXERCISE_NAME));
        when(exerciseNameMapper.toDto(EXERCISE_NAME)).thenReturn(EXERCISE_NAME_RESPONSE);

        ResponseExerciseNameDTO sut  = exerciseNameService.findById(1L);

        assertThat(sut).isEqualTo(EXERCISE_NAME_RESPONSE);
        verify(exerciseNameRepository).findById(1L);
        verify(exerciseNameMapper).toDto(EXERCISE_NAME);
    }

    @Test
    public void findById_WhenExerciseNameNotFound_ThrowsException() {
        when(exerciseNameRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseNameService.findById(99L)).isInstanceOf(RecordNotFoundException.class);
    }

    @Test
    public void update_WithValidData_ReturnsExerciseName(){
        ExerciseName exerciseNameUpdated  = new ExerciseName(1L, "Nome exercicio atualizado", MUSCLE_GROUP);
        ResponseExerciseNameDTO responseExerciseNameDTO = new ResponseExerciseNameDTO(1L, "Nome exercicio atualizado", MUSCLE_GROUP.getName());
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.of(EXERCISE_NAME));
        when(exerciseNameRepository.existsByExerciseNameIgnoreCase(EXERCISE_NAME_UPDATE_REQUEST.exerciseName())).thenReturn(false);
        when(muscleGroupRepository.findById(EXERCISE_NAME_UPDATE_REQUEST.idMuscleGroup())).thenReturn(Optional.of(MUSCLE_GROUP));
        when(exerciseNameRepository.save(EXERCISE_NAME)).thenReturn(exerciseNameUpdated);
        when(exerciseNameMapper.toDto(exerciseNameUpdated)).thenReturn(responseExerciseNameDTO);

        ResponseExerciseNameDTO sut = exerciseNameService.update(1L, EXERCISE_NAME_UPDATE_REQUEST);

        assertThat(sut).isEqualTo(responseExerciseNameDTO);
        verify(exerciseNameRepository).findById(1L);
        verify(exerciseNameRepository).existsByExerciseNameIgnoreCase(EXERCISE_NAME_UPDATE_REQUEST.exerciseName());
        verify(muscleGroupRepository).findById(EXERCISE_NAME_UPDATE_REQUEST.idMuscleGroup());
        verify(exerciseNameMapper).toDto(exerciseNameUpdated);
    }

    @ParameterizedTest
    @MethodSource("providerInvalidDataExerciseNames")
    public void update_WithInvalidData_ThrowsException(ExerciseName exerciseName) {
        ExerciseName exerciseNameUpdated  = new ExerciseName(1L, "Nome exercicio atualizado", MUSCLE_GROUP);
        ResponseExerciseNameDTO responseExerciseNameDTO = new ResponseExerciseNameDTO(1L, "Nome exercicio atualizado", MUSCLE_GROUP.getName());
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.of(EXERCISE_NAME));
        when(exerciseNameRepository.existsByExerciseNameIgnoreCase(EXERCISE_NAME_UPDATE_REQUEST.exerciseName())).thenReturn(false);
        when(muscleGroupRepository.findById(EXERCISE_NAME_UPDATE_REQUEST.idMuscleGroup())).thenReturn(Optional.of(MUSCLE_GROUP));
        when(exerciseNameRepository.save(exerciseName)).thenThrow(new RuntimeException());

        ResponseExerciseNameDTO sut = exerciseNameService.update(1L, EXERCISE_NAME_UPDATE_REQUEST);

        assertThat(sut).isEqualTo(responseExerciseNameDTO);
        verify(exerciseNameRepository).findById(1L);
        verify(exerciseNameRepository).existsByExerciseNameIgnoreCase(EXERCISE_NAME_UPDATE_REQUEST.exerciseName());
        verify(muscleGroupRepository).findById(EXERCISE_NAME_UPDATE_REQUEST.idMuscleGroup());
        verify(exerciseNameMapper).toDto(exerciseNameUpdated);
    }

    @Test
    public void update_WhenExerciseNameNotFound_ThrowsException() {
        when(exerciseNameRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> exerciseNameService.update(99L, EXERCISE_NAME_UPDATE_REQUEST));
    }

    @ParameterizedTest
    @MethodSource("providerInvalidExerciseNames")
    public void update_WhenExerciseNameInvalid_ThrowsException(RequestUpdateExerciseNameDTO requestUpdateExerciseNameDTO) {
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.of(EXERCISE_NAME));

        assertThrows(ApiErrorException.class, () -> exerciseNameService.update(1L, requestUpdateExerciseNameDTO));
        verify(exerciseNameRepository).findById(1L);
    }

    @Test
    public void update_WhenExerciseNameAlreadyExists_ThrowsException() {
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.of(EXERCISE_NAME));
        when(exerciseNameRepository.existsByExerciseNameIgnoreCase(EXERCISE_NAME_UPDATE_REQUEST.exerciseName())).thenReturn(true);

        assertThrows(InfoAlreadyExistsException.class, () -> exerciseNameService.update(1L, EXERCISE_NAME_UPDATE_REQUEST));
        verify(exerciseNameRepository).findById(1L);
        verify(exerciseNameRepository).existsByExerciseNameIgnoreCase(EXERCISE_NAME_UPDATE_REQUEST.exerciseName());
    }

    @Test
    public void update_WithUnexistingIDMuscleGroup_ThrowsException() {
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.of(EXERCISE_NAME));
        when(exerciseNameRepository.existsByExerciseNameIgnoreCase(EXERCISE_NAME_UPDATE_REQUEST.exerciseName())).thenReturn(false);
        when(muscleGroupRepository.findById(EXERCISE_NAME_UPDATE_REQUEST.idMuscleGroup())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> exerciseNameService.update(1L, EXERCISE_NAME_UPDATE_REQUEST));
        verify(exerciseNameRepository).findById(1L);
        verify(exerciseNameRepository).existsByExerciseNameIgnoreCase(EXERCISE_NAME_UPDATE_REQUEST.exerciseName());
    }

    @Test
    public void delete_WithExistingID_NotThrowsException() {
        when(exerciseNameRepository.findById(1L)).thenReturn(Optional.of(EXERCISE_NAME));

        assertThatCode(() -> exerciseNameService.delete(1L)).doesNotThrowAnyException();
        verify(exerciseNameRepository).findById(1L);
        verify(exerciseNameRepository).delete(EXERCISE_NAME);
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
                new ExerciseName(1L, "Nome exercicio 1", MUSCLE_GROUP),
                new ExerciseName(2L, "Nome exercicio 2", MUSCLE_GROUP),
                new ExerciseName(3L, "Nome exercicio 3", MUSCLE_GROUP)
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

    private static  Stream<Arguments> providerInvalidDataExerciseNames() {
        return Stream.of(
                Arguments.of(new ExerciseName(null, "Nome Exercicio", MUSCLE_GROUP)),
                Arguments.of(new ExerciseName(1L, null, MUSCLE_GROUP)),
                Arguments.of(new ExerciseName(null, "Nome Exercicio", null)),
                Arguments.of(new ExerciseName(null, "", MUSCLE_GROUP))
        );
    }

}
