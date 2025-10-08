package br.com.fitnessconsultant.repositories;

import br.com.fitnessconsultant.domain.entities.*;
import br.com.fitnessconsultant.domain.enums.Role;
import br.com.fitnessconsultant.domain.enums.TrainingType;
import br.com.fitnessconsultant.domain.repository.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class ExerciseRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Test
    public void create_WithValidData_ReturnsExercise() {
        Exercise exercise = exerciseRepository.save(this.createsExercise());

        Exercise sut = testEntityManager.find(Exercise.class, exercise.getId());

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(exercise);
    }


    @Test
    public void create_WithExerciseNameNull_ThrowsException() {
        User user = testEntityManager.persistAndFlush(User.builder().firstName("Nome").lastName("Sobrenome").email("email@email.com").password("12345").phone("77999999999").role(Role.ROLE_USER).isEnabled(true).build());
        Periodization periodization = testEntityManager.persistAndFlush(Periodization.builder().name("Nome periodization").numberWeeks(4).user(user).build());
        Training training = testEntityManager.persistAndFlush(Training.builder().trainingType(TrainingType.A).trainingName("Nome training").periodization(periodization).build());
        Exercise exercise = Exercise.builder().series(3).repetitions("Repetitions").initialLoad(30).finalLoad(40).method("method").exerciseName(null).training(training).build();

        assertThrows(DataIntegrityViolationException.class, () -> exerciseRepository.save(exercise));
    }

    @Test
    public void create_WithTrainingNull_ThrowsException() {
        MuscleGroup muscleGroup = testEntityManager.persistAndFlush(MuscleGroup.builder().name("Nome grupo muscular").build());
        ExerciseName exerciseName = testEntityManager.persistAndFlush(ExerciseName.builder().exerciseName("Exercise Name").muscleGroup(muscleGroup).build());
        Exercise exercise = Exercise.builder().series(3).repetitions("Repetitions").initialLoad(30).finalLoad(40).method("method").exerciseName(exerciseName).training(null).build();

        assertThrows(DataIntegrityViolationException.class, () -> exerciseRepository.save(exercise));
    }

    @Test
    public void create_WithInvalidData_ThrowsException() {
        MuscleGroup muscleGroup = testEntityManager.persistAndFlush(MuscleGroup.builder().name("Nome grupo muscular").build());
        ExerciseName exerciseName = testEntityManager.persistAndFlush(ExerciseName.builder().exerciseName("Exercise Name").muscleGroup(muscleGroup).build());
        User user = testEntityManager.persistAndFlush(User.builder().firstName("Nome").lastName("Sobrenome").email("email@email.com").password("12345").phone("77999999999").role(Role.ROLE_USER).isEnabled(true).build());
        Periodization periodization = testEntityManager.persistAndFlush(Periodization.builder().name("Nome periodization").numberWeeks(4).user(user).build());
        Training training = testEntityManager.persistAndFlush(Training.builder().trainingType(TrainingType.A).trainingName("Nome training").periodization(periodization).build());
        Exercise exercise = Exercise.builder()
                .series(3)
                .repetitions("Este texto contem mais de 100 caracteres incluindo espaços e assim violando a restrição definida no banco de dados")
                .initialLoad(30).finalLoad(40).method("Este texto contem mais de 100 caracteres incluindo espaços e assim violando a restrição definida no banco de dados")
                .exerciseName(exerciseName)
                .training(training)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> exerciseRepository.save(exercise));
    }

    @Test
    public void findById_WithExistingId_ReturnsExercise() {
        Exercise exercise = testEntityManager.persistFlushFind(this.createsExercise());

        Optional<Exercise> sut = exerciseRepository.findById(exercise.getId());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(exercise);
    }

    @Test
    public void findById_WithUnknownId_ReturnsExercise() {
        Optional<Exercise> sut = exerciseRepository.findById(99L);

        assertThat(sut).isEmpty();
    }

    @Test
    public void delete_WithExistingExercise_RemovePlanesFromDatabase() {
        Exercise exercise = testEntityManager.persistFlushFind(this.createsExercise());

        exerciseRepository.delete(exercise);

        Exercise removeExercise = testEntityManager.find(Exercise.class, exercise.getId());

        assertThat(removeExercise).isNull();
    }

    @Sql(scripts = "/sql/import_exercises.sql")
    @Test
    public void getAllExercisesByIdTraining_WithExistingId_ReturnsExercises() {
        List<Exercise> responseWithoutExercises = exerciseRepository.getAllExercisesByIdTraining(1L);

        assertThat(responseWithoutExercises).isNotEmpty();
        assertThat(responseWithoutExercises).hasSize(4);
        assertThat(responseWithoutExercises).extracting("id").containsExactly(1L, 2L, 3L, 4L);
    }

    @Test
    public void getAllExercisesByIdTraining_WithUnknownId_ReturnsEmptyList() {
        List<Exercise> responseWithoutExercises = exerciseRepository.getAllExercisesByIdTraining(99L);

        assertThat(responseWithoutExercises).isEmpty();
    }

    private Exercise createsExercise() {
        MuscleGroup muscleGroup = testEntityManager.persistAndFlush(MuscleGroup.builder().name("Nome grupo muscular").build());
        ExerciseName exerciseName = testEntityManager.persistAndFlush(ExerciseName.builder().exerciseName("Exercise Name").muscleGroup(muscleGroup).build());
        User user = testEntityManager.persistAndFlush(User.builder().firstName("Nome").lastName("Sobrenome").email("email@email.com").password("12345").phone("77999999999").role(Role.ROLE_USER).isEnabled(true).build());
        Periodization periodization = testEntityManager.persistAndFlush(Periodization.builder().name("Nome periodization").numberWeeks(4).user(user).build());
        Training training = testEntityManager.persistAndFlush(Training.builder().trainingType(TrainingType.A).trainingName("Nome training").periodization(periodization).build());
        return Exercise.builder().series(3).repetitions("Repetitions").initialLoad(30).finalLoad(40).method("method").exerciseName(exerciseName).training(training).build();
    }

}
