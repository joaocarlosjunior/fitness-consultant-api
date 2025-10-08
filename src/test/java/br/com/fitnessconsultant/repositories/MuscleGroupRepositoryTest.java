package br.com.fitnessconsultant.repositories;

import br.com.fitnessconsultant.domain.entities.MuscleGroup;
import br.com.fitnessconsultant.domain.repository.MuscleGroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class MuscleGroupRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private MuscleGroupRepository muscleGroupRepository;

    @Test
    public void create_WithValidData_ReturnsMuscleGroup() {
        MuscleGroup muscleGroup = muscleGroupRepository.save(this.createsMuscleGroupInstance());

        MuscleGroup sut = testEntityManager.find(MuscleGroup.class, muscleGroup.getId());

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(muscleGroup);
    }

    @Test
    public void create_WithExistingMuscleGroup_ThrowsException() {
        MuscleGroup muscleGroup1 = this.createsMuscleGroupInstance();
        MuscleGroup muscleGroup2 = this.createsMuscleGroupInstance();
        testEntityManager.persist(muscleGroup1);

        assertThrows(DataIntegrityViolationException.class, () -> muscleGroupRepository.save(muscleGroup2));
    }

    @ParameterizedTest
    @MethodSource("providersInvalidMuscleGroups")
    public void create_WithInvalidData_ThrowsException(MuscleGroup muscleGroup) {
        assertThrows(RuntimeException.class, () -> muscleGroupRepository.save(muscleGroup));
    }

    @Test
    public void existsByNameIgnoreCase_WithExistingMuscleGroup_ReturnsTrue() {
        MuscleGroup muscleGroup = testEntityManager.persistFlushFind(this.createsMuscleGroupInstance());

        boolean sut = muscleGroupRepository.existsByNameIgnoreCase(muscleGroup.getName());

        assertThat(sut).isTrue();
    }

    @Test
    public void existsByNameIgnoreCase_WithoutExistingMuscleGroup_ReturnsFalse() {
        boolean sut = muscleGroupRepository.existsByNameIgnoreCase("Nome grupo muscular");

        assertThat(sut).isFalse();
    }

    @Test
    public void findById_WithExistingID_ReturnsMuscleGroup() {
        MuscleGroup muscleGroup = testEntityManager.persistFlushFind(this.createsMuscleGroupInstance());

        Optional<MuscleGroup> sut = muscleGroupRepository.findById(muscleGroup.getId());

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(muscleGroup);
    }

    @Test
    public void findById_WithoutExistingID_ReturnsNull() {
        Optional<MuscleGroup> sut = muscleGroupRepository.findById(99L);

        assertThat(sut).isEmpty();
    }

    @Test
    public void delete_WithExistingMuscleGroup_RemovesMuscleGroupFromDatabase() {
        MuscleGroup muscleGroup = testEntityManager.persistFlushFind(this.createsMuscleGroupInstance());

        muscleGroupRepository.delete(muscleGroup);

        MuscleGroup sut = testEntityManager.find(MuscleGroup.class, muscleGroup.getId());

        assertThat(sut).isNull();
    }

    @Sql(scripts = "/sql/import_musclegroups.sql")
    @Test
    public void findAll_WithExistingMuscleGroup_ReturnsMuscleGroups() {
        List<MuscleGroup> listMuscleGroups = muscleGroupRepository.findAll();

        assertThat(listMuscleGroups).isNotEmpty();
        assertThat(listMuscleGroups).hasSize(4);
        assertThat(listMuscleGroups).extracting("name").containsExactly("Grupo Muscular A", "Grupo Muscular B", "Grupo Muscular C", "Grupo Muscular D");
    }

    @Test
    public void findAll_WithDoesNotExistsMuscleGroup_ReturnsEmptyList() {
        List<MuscleGroup> listMuscleGroups = muscleGroupRepository.findAll();

        assertThat(listMuscleGroups).isEmpty();
    }

    private static Stream<Arguments> providersInvalidMuscleGroups() {
        return Stream.of(
                Arguments.of(MuscleGroup.builder().name("").build()),
                Arguments.of(MuscleGroup.builder().name(null).build()),
                Arguments.of(MuscleGroup.builder().name("Este texto contem mais de 100 caracteres incluindo espaços e assim violando a restrição definida no banco de dados").build())
        );
    }

    private MuscleGroup createsMuscleGroupInstance(){
        return MuscleGroup.builder().name("Grupo Muscular").build();
    }
}
