package br.com.fitnessconsultant.services;

import br.com.fitnessconsultant.domain.entities.Periodization;
import br.com.fitnessconsultant.domain.entities.Training;
import br.com.fitnessconsultant.domain.repository.PeriodizationRepository;
import br.com.fitnessconsultant.domain.repository.TrainingRepository;
import br.com.fitnessconsultant.dto.training.RequestTrainingDTO;
import br.com.fitnessconsultant.dto.training.RequestUpdateTrainingDTO;
import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.factories.PeriodizationFactory;
import br.com.fitnessconsultant.factories.TrainingFactory;
import br.com.fitnessconsultant.mappers.TrainingMapper;
import br.com.fitnessconsultant.service.training.impl.TrainingServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {
    @InjectMocks
    private TrainingServiceImpl trainingService;
    @Mock
    private TrainingRepository trainingRepository;
    @Mock
    private PeriodizationRepository periodizationRepository;
    @Mock
    private TrainingMapper trainingMapper;
    private Training training;
    private RequestTrainingDTO trainingRequest;
    private ResponseTrainingDTO trainingResponse;
    private RequestUpdateTrainingDTO updateTrainingRequest;
    private List<Training> trainings;
    private List<ResponseTrainingDTO> trainingsResponse;
    private Periodization periodization;

    @BeforeEach
    void setUp() {
        training = TrainingFactory.createTraining();
        trainingRequest = TrainingFactory.createTrainingRequest();
        trainingResponse = TrainingFactory.createTrainingResponse();
        updateTrainingRequest = TrainingFactory.createUpdateTrainingRequest();
        trainings = TrainingFactory.createTrainingList();
        trainingsResponse = TrainingFactory.createTrainingResponseList();
        periodization = PeriodizationFactory.createPeriodization();
    }

    @Test
    public void create_WithValidData_ReturnsTraining(){
        when(periodizationRepository.findById(trainingRequest.idPeriodization())).thenReturn(Optional.of(periodization));
        when(trainingMapper.toEntity(trainingRequest, periodization)).thenReturn(training);
        when(trainingRepository.save(training)).thenReturn(training);
        when(trainingMapper.toDto(training)).thenReturn(trainingResponse);

        ResponseTrainingDTO sut = trainingService.create(trainingRequest);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(trainingResponse);
        verify(periodizationRepository).findById(trainingRequest.idPeriodization());
        verify(trainingMapper).toEntity(trainingRequest, periodization);
        verify(trainingRepository).save(training);
        verify(trainingMapper).toDto(training);
    }

    @Test
    public void create_WithUnexistingPeriodizationID_ThrowsException(){
        when(periodizationRepository.findById(trainingRequest.idPeriodization())).thenReturn(Optional.empty());

        assertThrowsExactly(RecordNotFoundException.class, () -> trainingService.create(trainingRequest));
        verify(periodizationRepository).findById(trainingRequest.idPeriodization());
    }

    @Test
    public void update_WithValidData_ReturnsTraining(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));
        when(periodizationRepository.findById(updateTrainingRequest.idPeriodization())).thenReturn(Optional.of(periodization));
        when(trainingRepository.save(training)).thenReturn(training);
        when(trainingMapper.toDto(training)).thenReturn(trainingResponse);

        ResponseTrainingDTO sut = trainingService.update(1L, updateTrainingRequest);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(trainingResponse);
        verify(trainingRepository).findById(updateTrainingRequest.idPeriodization());
        verify(periodizationRepository).findById(updateTrainingRequest.idPeriodization());
        verify(trainingRepository).save(training);
        verify(trainingMapper).toDto(training);
    }

    @Test
    public void update_WithUnexistingTrainingID_ThrowsException(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrowsExactly(RecordNotFoundException.class, () -> trainingService.update(1L, updateTrainingRequest));
        verify(trainingRepository).findById(updateTrainingRequest.idPeriodization());
    }

    @Test
    public void update_WithUnexistingPeriodizationID_ThrowsException(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));
        when(periodizationRepository.findById(updateTrainingRequest.idPeriodization())).thenReturn(Optional.empty());

        assertThrowsExactly(RecordNotFoundException.class, () -> trainingService.update(1L, updateTrainingRequest));
        verify(trainingRepository).findById(updateTrainingRequest.idPeriodization());
        verify(periodizationRepository).findById(updateTrainingRequest.idPeriodization());
    }

    @Test
    public void delete_WithExistingTrainingID_DoesNotThrowException(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));

        assertThatCode(() -> trainingService.delete(1L)).doesNotThrowAnyException();
        verify(trainingRepository).findById(1L);
        verify(trainingRepository).delete(training);
    }

    @Test
    public void delete_WithUnexistingTrainingID_ThrowsException(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrowsExactly(RecordNotFoundException.class, () -> trainingService.delete(1L));
        verify(trainingRepository).findById(1L);
    }

    @Test
    public void findById_WithExistingTrainingID_ReturnsTraining(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingResponse);

        ResponseTrainingDTO sut = trainingService.findById(1L);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(trainingResponse);
        verify(trainingRepository).findById(1L);
        verify(trainingMapper).toDto(training);
    }

    @Test
    public void findById_WithUnexistingTrainingID_ThrowsException(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrowsExactly(RecordNotFoundException.class, () -> trainingService.findById(1L));
        verify(trainingRepository).findById(1L);
    }

    @Test
    public void getAllTrainingByIdPeriodization_WhenHaveTrainingsForPeriodizationID_ReturnsTraining(){
        when(periodizationRepository.findById(1L)).thenReturn(Optional.of(periodization));
        when(trainingRepository.getAllTrainingByIdPeriodization(1L)).thenReturn(trainings);
        when(trainingMapper.toDto(any(Training.class))).thenAnswer(
                invocation -> {
                    Training training = invocation.getArgument(0, Training.class);
                    return ResponseTrainingDTO.builder()
                            .idTraining(training.getId())
                            .idPeriodization(periodization.getId())
                            .trainingName(training.getTrainingName())
                            .trainingType(training.getTrainingName())
                            .createdAt(training.getCreatedAt().toString())
                            .updatedAt(training.getUpdatedAt().toString())
                            .build();
                }
        );

        List<ResponseTrainingDTO> sut = trainingService.getAllTrainingByIdPeriodization(1L);

        assertThat(sut).isNotNull();
        assertThat(sut.size()).isEqualTo(trainingsResponse.size());
        assertThat(sut).extracting("idTraining").containsExactly(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
        verify(periodizationRepository).findById(1L);
        verify(trainingRepository).getAllTrainingByIdPeriodization(1L);
    }

    @Test
    public void getAllTrainingByIdPeriodization_WithUnexistingPeriodizationID_ThrowsException(){
        when(periodizationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrowsExactly(RecordNotFoundException.class, () -> trainingService.getAllTrainingByIdPeriodization(1L));
        verify(periodizationRepository).findById(1L);
    }
}
