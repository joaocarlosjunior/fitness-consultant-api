package br.com.fitnessconsultant.repositories;

import br.com.fitnessconsultant.domain.entities.Periodization;
import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.enums.Role;
import br.com.fitnessconsultant.domain.repository.PeriodizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class PeriodizationRespositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private PeriodizationRepository periodizationRepository;

    private static int count;

    @BeforeEach
    void cleanDatabase() {
        periodizationRepository.deleteAll();
    }

    @Test
    public void create_WithValidData_ReturnsPeriodization() {
        Periodization periodization = createsPeriodization();
        Periodization sut = periodizationRepository.save(periodization);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(periodization);
    }

    @ParameterizedTest
    @MethodSource("providersInvalidDataPeriodization")
    public void create_WithInvalidData_ThrowsException(String name, Integer numberWeeks, boolean includeUser) {
        User user = includeUser ? createsUser() : null;

        assertThrows(RuntimeException.class, () -> periodizationRepository.save(
                Periodization.builder().name(name).numberWeeks(numberWeeks).user(user).build()
        ));
    }

    private static Stream<Arguments> providersInvalidDataPeriodization() {
        return Stream.of(
                Arguments.of(null, 4, true),
                Arguments.of("", 4, true),
                Arguments.of("Nome Periodizacao", null, true),
                Arguments.of("Nome Periodizacao", 4, false)
        );
    }

    private User createsUser() {
        return testEntityManager.persistAndFlush(
                User.builder()
                        .firstName("Nome usuario")
                        .lastName("Sobrenome usuario")
                        .email(UUID.randomUUID() + "@email.com")
                        .password("12345")
                        .phone("77999999999")
                        .role(Role.ROLE_USER)
                        .isEnabled(true)
                        .build()
        );
    }

    private Periodization createsPeriodization() {
        User user = createsUser();
        return Periodization.builder().name("Nome Periodizacao").numberWeeks(4).user(user).build();
    }
}
