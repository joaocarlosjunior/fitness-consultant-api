package br.com.fitnessconsultant.services;

import br.com.fitnessconsultant.domain.entities.Periodization;
import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.repository.PeriodizationRepository;
import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.exception.UserNotFoundException;
import br.com.fitnessconsultant.factories.PeriodizationFactory;
import br.com.fitnessconsultant.factories.UserFactory;
import br.com.fitnessconsultant.mappers.PeriodizationMapper;
import br.com.fitnessconsultant.service.periodization.impl.PeriodizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PeriodizationServiceTest {
    @InjectMocks
    private PeriodizationServiceImpl periodizationService;
    @Mock
    private PeriodizationRepository periodizationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PeriodizationMapper periodizationMapper;
    private Periodization periodization;
    private RequestPeriodizationDTO periodizationRequest;
    private ResponsePeriodizationDTO periodizationResponse;
    private UpdatePeriodizationDTO updatePeriodizationRequest;
    private List<Periodization> periodizationList;
    private User user;

    @BeforeEach
    void setUp() {
        periodization = PeriodizationFactory.createPeriodization();
        periodizationRequest = PeriodizationFactory.createPeriodizationRequest();
        periodizationResponse = PeriodizationFactory.createPeriodizationResponse();
        updatePeriodizationRequest = PeriodizationFactory.createUpdatePeriodization();
        periodizationList = PeriodizationFactory.createPeriodizationList();
        user = UserFactory.createUser();
    }

    @Test
    public void create_WithValidData_ReturnsPeriodization() {
        when(userRepository.findById(periodizationRequest.idUser())).thenReturn(Optional.of(user));
        when(periodizationMapper.toEntity(periodizationRequest, user)).thenReturn(periodization);
        when(periodizationRepository.save(periodization)).thenReturn(periodization);
        when(periodizationMapper.toDto(periodization)).thenReturn(periodizationResponse);

        ResponsePeriodizationDTO sut = periodizationService.create(periodizationRequest);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(periodizationResponse);
        verify(userRepository).findById(periodizationRequest.idUser());
        verify(periodizationMapper).toEntity(periodizationRequest, user);
        verify(periodizationRepository).save(periodization);
        verify(periodizationMapper).toDto(periodization);
    }

    @Test
    public void create_WithUnexistingUserID_ThrowsException() {
        when(userRepository.findById(periodizationRequest.idUser())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> periodizationService.create(periodizationRequest));
        verify(userRepository).findById(periodizationRequest.idUser());
    }

    @Test
    public void update_WithValidData_ReturnsPeriodization() {
        when(periodizationRepository.findById(1L)).thenReturn(Optional.of(periodization));
        when(userRepository.findById(updatePeriodizationRequest.idUser())).thenReturn(Optional.of(user));
        when(periodizationRepository.save(periodization)).thenReturn(periodization);
        when(periodizationMapper.toDto(periodization)).thenReturn(periodizationResponse);

        ResponsePeriodizationDTO sut = periodizationService.update(1L, updatePeriodizationRequest);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(periodizationResponse);
        verify(periodizationRepository).findById(1L);
        verify(userRepository).findById(updatePeriodizationRequest.idUser());
        verify(periodizationRepository).save(periodization);
        verify(periodizationMapper).toDto(periodization);
    }

    @Test
    public void update_WithUnexistingPeriodizationID_ThrowsException() {
        when(periodizationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrowsExactly(RecordNotFoundException.class, () -> periodizationService.update(99L, updatePeriodizationRequest));
        verify(periodizationRepository).findById(99L);
    }

    @Test
    public void update_WithUnexistingUserID_ThrowsException() {
        when(periodizationRepository.findById(1L)).thenReturn(Optional.of(periodization));
        when(userRepository.findById(updatePeriodizationRequest.idUser())).thenReturn(Optional.empty());

        assertThrowsExactly(UserNotFoundException.class, () -> periodizationService.update(1L, updatePeriodizationRequest));
        verify(periodizationRepository).findById(1L);
        verify(userRepository).findById(updatePeriodizationRequest.idUser());
    }

    @Test
    public void findById_WithExistingId_ReturnsPeriodization() {
        when(periodizationRepository.findById(1L)).thenReturn(Optional.of(periodization));
        when(periodizationMapper.toDto(periodization)).thenReturn(periodizationResponse);

        ResponsePeriodizationDTO sut = periodizationService.findById(1L);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(periodizationResponse);
        verify(periodizationRepository).findById(1L);
        verify(periodizationMapper).toDto(periodization);
    }

    @Test
    public void findById_WithUnexistingId_ThrowsException() {
        when(periodizationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> periodizationService.findById(99L));
        verify(periodizationRepository).findById(99L);
    }

    @Test
    public void delete_WithExistingId_DoesNotThrowAnyException() {
        when(periodizationRepository.findById(1L)).thenReturn(Optional.of(periodization));

        assertThatCode(() -> periodizationService.delete(1L)).doesNotThrowAnyException();
        verify(periodizationRepository).findById(1L);
        verify(periodizationRepository).delete(periodization);
    }

    @Test
    public void delete_WithUnexistingId_ThrowsException() {
        when(periodizationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> periodizationService.delete(1L));
        verify(periodizationRepository).findById(1L);
    }

    @Test
    public void list_WhenThereRegisteredData_ReturnsPeriodizations() {
        when(periodizationRepository.findAll()).thenReturn(periodizationList);
        when(periodizationMapper.toDto(any(Periodization.class))).thenAnswer(
                invocation -> {
                    Periodization periodization = invocation.getArgument(0, Periodization.class);
                    return ResponsePeriodizationDTO.builder()
                            .idPeriodization(periodization.getId())
                            .name(periodization.getName())
                            .numberWeeks(periodization.getNumberWeeks())
                            .startDate(periodization.getStarDate().toString())
                            .createdAt(periodization.getCreatedAt().toString())
                            .updatedAt(periodization.getUpdatedAt().toString())
                            .build();
                }
        );
        List<ResponsePeriodizationDTO> sut = periodizationService.list();

        assertThat(sut).isNotNull();
        assertThat(sut.size()).isEqualTo(periodizationList.size());
        assertThat(sut).extracting("idPeriodization").containsExactly(1L, 2L, 3L, 4L);
        assertThat(sut).extracting("name").containsExactly("Nome Periodizacao 1", "Nome Periodizacao 2", "Nome Periodizacao 3", "Nome Periodizacao 4");
        verify(periodizationRepository).findAll();
    }

    @Test
    public void list_WhenNotRegisteredData_ReturnsEmptyList() {
        when(periodizationRepository.findAll()).thenReturn(List.of());

        List<ResponsePeriodizationDTO> sut = periodizationService.list();

        assertThat(sut).isNotNull();
        assertThat(sut).isEmpty();
        verify(periodizationRepository).findAll();
    }

    @Test
    public void getAllPeriodizationByUser_WhenUserExists_ReturnsPeriodizations() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(periodizationRepository.getAllPeriodizationByIdUser(user.getId())).thenReturn(periodizationList);
        when(periodizationMapper.toDto(any(Periodization.class))).thenAnswer(
                invocation -> {
                    Periodization periodization = invocation.getArgument(0, Periodization.class);
                    return ResponsePeriodizationDTO.builder()
                            .idPeriodization(periodization.getId())
                            .name(periodization.getName())
                            .numberWeeks(periodization.getNumberWeeks())
                            .startDate(periodization.getStarDate().toString())
                            .createdAt(periodization.getCreatedAt().toString())
                            .updatedAt(periodization.getUpdatedAt().toString())
                            .build();
                }
        );

        List<ResponsePeriodizationDTO> sut = periodizationService.getAllPeriodizationByUser(1L);

        assertThat(sut).isNotNull();
        assertThat(sut.size()).isEqualTo(periodizationList.size());
        assertThat(sut).extracting("idPeriodization").containsExactly(1L, 2L, 3L, 4L);
        assertThat(sut).extracting("name").containsExactly("Nome Periodizacao 1", "Nome Periodizacao 2", "Nome Periodizacao 3", "Nome Periodizacao 4");
        verify(userRepository).findById(1L);
        verify(periodizationRepository).getAllPeriodizationByIdUser(1L);
    }

    @Test
    public void getAllPeriodizationByUser_WithUnexistingUser_ThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> periodizationService.getAllPeriodizationByUser(1L));
        verify(userRepository).findById(1L);
    }

    @Test
    public void getAllPeriodizationByUser_UserHasNoRegisteredPeriodization_ReturnsEmptyList() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(periodizationRepository.getAllPeriodizationByIdUser(user.getId())).thenReturn(List.of());

        List<ResponsePeriodizationDTO> sut = periodizationService.getAllPeriodizationByUser(1L);

        assertThat(sut).isNotNull();
        assertThat(sut).isEmpty();
        verify(userRepository).findById(1L);
        verify(periodizationRepository).getAllPeriodizationByIdUser(1L);
    }
}
