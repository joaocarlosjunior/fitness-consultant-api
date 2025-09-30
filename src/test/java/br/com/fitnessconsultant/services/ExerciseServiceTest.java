package br.com.fitnessconsultant.services;

import br.com.fitnessconsultant.domain.entities.Exercise;
import br.com.fitnessconsultant.domain.repository.ExerciseNameRepository;
import br.com.fitnessconsultant.domain.repository.ExerciseRepository;
import br.com.fitnessconsultant.domain.repository.TrainingRepository;
import br.com.fitnessconsultant.dto.exercise.ResponseExerciseDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.mappers.ExerciseMapper;
import br.com.fitnessconsultant.service.exercise.impl.ExerciseServiceImpl;
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

import static br.com.fitnessconsultant.common.ExerciseConstants.*;
import static br.com.fitnessconsultant.common.ExerciseNameConstants.EXERCISE_NAME;
import static br.com.fitnessconsultant.common.TrainingConstants.TRAINING;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceTest {
    @InjectMocks
    private ExerciseServiceImpl exerciseService;
    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private ExerciseNameRepository exerciseNameRepository;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private ExerciseMapper exerciseMapper;

    @Test
    public void create_WithDataValid_returnExercise() {
        when(exerciseNameRepository.getReferenceById(EXERCISE_REQUEST.idExerciseName())).thenReturn(EXERCISE_NAME);
        when(trainingRepository.getReferenceById(EXERCISE_REQUEST.idTraining())).thenReturn(TRAINING);
        when(exerciseMapper.toEntity(EXERCISE_REQUEST, EXERCISE_NAME, TRAINING)).thenReturn(EXERCISE);
        when(exerciseRepository.save(EXERCISE)).thenReturn(EXERCISE);
        when(exerciseMapper.toDto(EXERCISE)).thenReturn(EXERCISE_RESPONSE);

        ResponseExerciseDTO sut = exerciseService.create(EXERCISE_REQUEST);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(EXERCISE_RESPONSE);

        verify(exerciseNameRepository).getReferenceById(1L);
        verify(trainingRepository).getReferenceById(1L);
        verify(exerciseMapper).toEntity(EXERCISE_REQUEST, EXERCISE_NAME, TRAINING);
        verify(exerciseRepository).save(EXERCISE);
        verify(exerciseMapper).toDto(EXERCISE);
    }

    @ParameterizedTest
    @MethodSource("providersInvalidExercises")
    public void create_WithInvalidData_ThrowsException(Exercise exercise) {
        when(exerciseRepository.save(exercise)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> exerciseService.create(EXERCISE_REQUEST)).isInstanceOf(RuntimeException.class);
        verify(exerciseNameRepository).getReferenceById(1L);
        verify(trainingRepository).getReferenceById(1L);
    }

    @Test
    public void create_WithUnexistingIdExerciseName_ThrowsEntityNotFoundException() {
        when(exerciseNameRepository.getReferenceById(EXERCISE_REQUEST.idExerciseName())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> exerciseService.create(EXERCISE_REQUEST)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void create_WithUnexistingIdTraining_ThrowsEntityNotFoundException() {
        when(trainingRepository.getReferenceById(EXERCISE_REQUEST.idTraining())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> exerciseService.create(EXERCISE_REQUEST)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void update_WithDataValid_returnExercise() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(EXERCISE));
        when(exerciseRepository.save(EXERCISE)).thenReturn(EXERCISE);
        when(exerciseMapper.toDto(EXERCISE)).thenReturn(EXERCISE_RESPONSE);

        ResponseExerciseDTO sut = exerciseService.update(1L, EXERCISE_REQUEST);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(EXERCISE_RESPONSE);
        verify(exerciseRepository).findById(1L);
        verify(exerciseRepository).save(EXERCISE);
        verify(exerciseMapper).toDto(EXERCISE);
    }

    @Test
    public void update_ByUnexistingIdExercise_ThrowsExeception() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseService.update(1L, EXERCISE_REQUEST))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage("Exercício não encontrado");
    }

    @Test
    public void delete_WithExistingId_DoesNotThrowAnyException() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(EXERCISE));

        assertThatCode(() -> exerciseService.delete(1L)).doesNotThrowAnyException();
        verify(exerciseRepository).findById(1L);
    }

    @Test
    public void delete_ByUnexistingId_ThrowsExeception() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseService.delete(1L)).isInstanceOf(RecordNotFoundException.class).hasMessage("Exercício não encontrado");
    }

    @Test
    public void getAllExercisesByIdTraining_WithExistingId_ReturnsExercises() {
        List<Exercise> exercises = List.of(
                new Exercise(1L, 1, "Repeticoes", 10, 20, "Metodo", EXERCISE_NAME, TRAINING),
                new Exercise(2L, 1, "Repeticoes", 20, 30, "Metodo", EXERCISE_NAME, TRAINING),
                new Exercise(3L, 1, "Repeticoes", 30, 40, "Metodo", EXERCISE_NAME, TRAINING),
                new Exercise(4L, 1, "Repeticoes", 40, 50, "Metodo", EXERCISE_NAME, TRAINING)
        );
        when(trainingRepository.existsTrainingsById(1L)).thenReturn(true);
        when(exerciseRepository.getAllExercisesByIdTraining(1L)).thenReturn(exercises);
        when(exerciseMapper.toDto(any(Exercise.class)))
                .thenAnswer(invocation -> {
                    Exercise ex = invocation.getArgument(0);
                    return ResponseExerciseDTO.builder()
                            .idExercise(ex.getId())
                            .exerciseName(ex.getExerciseName().getExerciseName())
                            .repetitions(ex.getRepetitions())
                            .series(ex.getSeries())
                            .initialLoad(ex.getInitialLoad())
                            .finalLoad(ex.getFinalLoad())
                            .method(ex.getMethod())
                            .build();
                });

        List<ResponseExerciseDTO> sut = exerciseService.getAllExercisesByIdTraining(1L);

        assertThat(sut).isNotNull();
        assertThat(sut).hasSize(exercises.size());
        assertThat(sut).extracting("idExercise").containsExactly(1L, 2L, 3L, 4L);
        verify(trainingRepository).existsTrainingsById(1L);
        verify(exerciseRepository).getAllExercisesByIdTraining(1L);
        verify(exerciseMapper, times(4)).toDto(any(Exercise.class));
    }

    @Test
    public void getAllExercisesByIdTraining_WithUnexistingId_ThrowsException() {
        when(trainingRepository.existsTrainingsById(99L)).thenThrow(new RecordNotFoundException("Exercício não encontrado"));

        assertThatThrownBy(() -> exerciseService.getAllExercisesByIdTraining(99L))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage("Exercício não encontrado");
    }

    private static Stream<Arguments> providersInvalidExercises() {
        return Stream.of(
                Arguments.of(new Exercise(null, 10, "Repeticoes", 10, 20, "Metodo", EXERCISE_NAME, TRAINING)),
                Arguments.of(new Exercise(1L, 10, "Numero Maximo de Caracteres Ultrapassado", 10, 20, "Metodo", EXERCISE_NAME, TRAINING)),
                Arguments.of(new Exercise(1L, 10, "Repeticoes", 10, 20, "Numero Maximo de Caracteres Ultrapassado", EXERCISE_NAME, TRAINING)),
                Arguments.of(new Exercise(1L, 10, "Repeticoes", 10, 20, "Metodo", null, TRAINING)),
                Arguments.of(new Exercise(1L, 10, "Repeticoes", 10, 20, "Metodo", EXERCISE_NAME, null))
        );
    }
}
