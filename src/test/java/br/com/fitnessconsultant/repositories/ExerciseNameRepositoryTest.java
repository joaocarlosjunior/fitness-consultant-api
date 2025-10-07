package br.com.fitnessconsultant.repositories;

import br.com.fitnessconsultant.domain.entities.ExerciseName;
import br.com.fitnessconsultant.domain.entities.MuscleGroup;
import br.com.fitnessconsultant.domain.repository.ExerciseNameRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class ExerciseNameRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private ExerciseNameRepository exerciseNameRepository;

    @Test
    public void create_WithValidData_ReturnsExerciseName() {
        MuscleGroup muscleGroup = testEntityManager.persistAndFlush(MuscleGroup.builder().name("Nome grupo muscular").build());
        ExerciseName exerciseName = new ExerciseName("Nome exercicio", muscleGroup);
        exerciseNameRepository.save(exerciseName);

        ExerciseName sut = testEntityManager.find(ExerciseName.class, exerciseName.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getExerciseName()).isEqualTo(exerciseName.getExerciseName());
    }

    @ParameterizedTest
    @MethodSource("providesInvalidExerciseNames")
    public void create_WithInvalidData_ThrowsException(ExerciseName exerciseName) {
        MuscleGroup muscleGroup = testEntityManager.persistAndFlush(MuscleGroup.builder().name("Nome grupo muscular").build());
        exerciseName.setMuscleGroup(muscleGroup);

        assertThrows(ConstraintViolationException.class, () -> exerciseNameRepository.save(exerciseName));
    }

    @Test
    public void create_WithInvalidMuscleGroup_ThrowsException() {
        ExerciseName exerciseName = new ExerciseName("Nome exercicio", null);
        assertThrows(DataIntegrityViolationException.class, () -> exerciseNameRepository.save(exerciseName));
    }

    @Test
    public void existsByExerciseNameIgnoreCase_WithExistingExerciseName_ReturnsBoolean() {
        MuscleGroup muscleGroup = testEntityManager.persistAndFlush(MuscleGroup.builder().name("Nome grupo muscular").build());
        ExerciseName exerciseName = new ExerciseName("Nome exercicio", muscleGroup);
        testEntityManager.persist(exerciseName);

        boolean sut = exerciseNameRepository.existsByExerciseNameIgnoreCase(exerciseName.getExerciseName());

        assertThat(sut).isTrue();
    }

    @Test
    public void existsByExerciseNameIgnoreCase_WithUnexistingExerciseName_ReturnsBoolean() {
        boolean sut = exerciseNameRepository.existsByExerciseNameIgnoreCase("Nome exercicio teste");

        assertThat(sut).isFalse();
    }

    @Test
    public void findById_WithExistingExerciseName_ReturnsExerciseName() {
        MuscleGroup muscleGroup = testEntityManager.persistAndFlush(MuscleGroup.builder().name("Nome grupo muscular").build());
        ExerciseName exerciseName = testEntityManager.persistAndFlush(new ExerciseName("Nome exercicio", muscleGroup));

        Optional<ExerciseName> sut = exerciseNameRepository.findById(exerciseName.getId());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get().getExerciseName()).isEqualTo(exerciseName.getExerciseName());
    }

    @Test
    public void findById_WithUnexistingExerciseName_ReturnsEmpty() {
        Optional<ExerciseName> sut = exerciseNameRepository.findById(99L);

        assertThat(sut).isEmpty();
    }

    @Test
    public void delete_WithExistingExerciseName_RemovesExerciseNameFromDatabase() {
        MuscleGroup muscleGroup = testEntityManager.persistAndFlush(MuscleGroup.builder().name("Nome grupo muscular").build());
        ExerciseName exerciseName = testEntityManager.persistAndFlush(new ExerciseName("Nome exercicio", muscleGroup));

        exerciseNameRepository.delete(exerciseName);

        ExerciseName sut = testEntityManager.find(ExerciseName.class, exerciseName.getId());

        assertThat(sut).isNull();
    }

    private static Stream<Arguments> providesInvalidExerciseNames() {
        return Stream.of(
                Arguments.of(ExerciseName.builder().exerciseName("").build()),
                Arguments.of(ExerciseName.builder().exerciseName(null).build())
        );
    }
}
