package br.com.fitnessconsultant.services;

import br.com.fitnessconsultant.domain.entities.Training;
import br.com.fitnessconsultant.domain.repository.PeriodizationRepository;
import br.com.fitnessconsultant.domain.repository.TrainingRepository;
import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.mappers.TrainingMapper;
import br.com.fitnessconsultant.service.training.impl.TrainingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static br.com.fitnessconsultant.common.PeriodizationConstants.PERIODIZATION;
import static br.com.fitnessconsultant.common.TrainingConstants.*;
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

    @Test
    public void create_WithValidData_ReturnsTraining(){
        when(periodizationRepository.findById(TRAINING_REQUEST.idPeriodization())).thenReturn(Optional.of(PERIODIZATION));
        when(trainingMapper.toEntity(TRAINING_REQUEST, PERIODIZATION)).thenReturn(TRAINING);
        when(trainingRepository.save(TRAINING)).thenReturn(TRAINING);
        when(trainingMapper.toDto(TRAINING)).thenReturn(RESPONSE_TRAINING);

        ResponseTrainingDTO sut = trainingService.create(TRAINING_REQUEST);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(RESPONSE_TRAINING);
        verify(periodizationRepository).findById(TRAINING_REQUEST.idPeriodization());
        verify(trainingMapper).toEntity(TRAINING_REQUEST, PERIODIZATION);
        verify(trainingRepository).save(TRAINING);
        verify(trainingMapper).toDto(TRAINING);
    }

    @Test
    public void create_WithUnexistingPeriodizationID_ThrowsException(){
        when(periodizationRepository.findById(TRAINING_REQUEST.idPeriodization())).thenReturn(Optional.empty());

        assertThrowsExactly(RecordNotFoundException.class, () -> trainingService.create(TRAINING_REQUEST));
        verify(periodizationRepository).findById(TRAINING_REQUEST.idPeriodization());
    }

    @Test
    public void update_WithValidData_ReturnsTraining(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(TRAINING));
        when(periodizationRepository.findById(REQUEST_UPDATE_TRAINING.idPeriodization())).thenReturn(Optional.of(PERIODIZATION));
        when(trainingRepository.save(TRAINING)).thenReturn(TRAINING);
        when(trainingMapper.toDto(TRAINING)).thenReturn(RESPONSE_TRAINING);

        ResponseTrainingDTO sut = trainingService.update(1L, REQUEST_UPDATE_TRAINING);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(RESPONSE_TRAINING);
        verify(trainingRepository).findById(REQUEST_UPDATE_TRAINING.idPeriodization());
        verify(periodizationRepository).findById(REQUEST_UPDATE_TRAINING.idPeriodization());
        verify(trainingRepository).save(TRAINING);
        verify(trainingMapper).toDto(TRAINING);
    }

    @Test
    public void update_WithUnexistingTrainingID_ThrowsException(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrowsExactly(RecordNotFoundException.class, () -> trainingService.update(1L, REQUEST_UPDATE_TRAINING));
        verify(trainingRepository).findById(REQUEST_UPDATE_TRAINING.idPeriodization());
    }

    @Test
    public void update_WithUnexistingPeriodizationID_ThrowsException(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(TRAINING));
        when(periodizationRepository.findById(REQUEST_UPDATE_TRAINING.idPeriodization())).thenReturn(Optional.empty());

        assertThrowsExactly(RecordNotFoundException.class, () -> trainingService.update(1L, REQUEST_UPDATE_TRAINING));
        verify(trainingRepository).findById(REQUEST_UPDATE_TRAINING.idPeriodization());
        verify(periodizationRepository).findById(REQUEST_UPDATE_TRAINING.idPeriodization());
    }

    @Test
    public void delete_WithExistingTrainingID_DoesNotThrowException(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(TRAINING));

        assertThatCode(() -> trainingService.delete(1L)).doesNotThrowAnyException();
        verify(trainingRepository).findById(1L);
        verify(trainingRepository).delete(TRAINING);
    }

    @Test
    public void delete_WithUnexistingTrainingID_ThrowsException(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrowsExactly(RecordNotFoundException.class, () -> trainingService.delete(1L));
        verify(trainingRepository).findById(1L);
    }

    @Test
    public void findById_WithExistingTrainingID_ReturnsTraining(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(TRAINING));
        when(trainingMapper.toDto(TRAINING)).thenReturn(RESPONSE_TRAINING);

        ResponseTrainingDTO sut = trainingService.findById(1L);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(RESPONSE_TRAINING);
        verify(trainingRepository).findById(1L);
        verify(trainingMapper).toDto(TRAINING);
    }

    @Test
    public void findById_WithUnexistingTrainingID_ThrowsException(){
        when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrowsExactly(RecordNotFoundException.class, () -> trainingService.findById(1L));
        verify(trainingRepository).findById(1L);
    }

    @Test
    public void getAllTrainingByIdPeriodization_WhenHaveTrainingsForPeriodizationID_ReturnsTraining(){
        when(periodizationRepository.findById(1L)).thenReturn(Optional.of(PERIODIZATION));
        when(trainingRepository.getAllTrainingByIdPeriodization(1L)).thenReturn(TRAININGS);
        when(trainingMapper.toDto(any(Training.class))).thenAnswer(
                invocation -> {
                    Training training = invocation.getArgument(0, Training.class);
                    return ResponseTrainingDTO.builder()
                            .idTraining(training.getId())
                            .idPeriodization(PERIODIZATION.getId())
                            .trainingName(training.getTrainingName())
                            .trainingType(training.getTrainingName())
                            .createdAt(training.getCreatedAt().toString())
                            .updatedAt(training.getUpdatedAt().toString())
                            .build();
                }
        );

        List<ResponseTrainingDTO> sut = trainingService.getAllTrainingByIdPeriodization(1L);

        assertThat(sut).isNotNull();
        assertThat(sut.size()).isEqualTo(RESPONSE_TRAININGS.size());
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
