package br.com.personalgymapi.service.training.impl;

import br.com.personalgymapi.domain.entities.Periodization;
import br.com.personalgymapi.domain.entities.Training;
import br.com.personalgymapi.domain.enums.TrainingType;
import br.com.personalgymapi.domain.repository.PeriodizationRepository;
import br.com.personalgymapi.domain.repository.TrainingRepository;
import br.com.personalgymapi.dto.training.RecoveryTrainingDTO;
import br.com.personalgymapi.dto.training.RegisterTrainingDTO;
import br.com.personalgymapi.service.training.TrainingService;
import br.com.personalgymapi.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;
    private final PeriodizationRepository periodizationRepository;

    @Transactional
    public RecoveryTrainingDTO createTraining(RegisterTrainingDTO registerTrainingDTO) {

        Periodization periodization = periodizationRepository
                .findById(registerTrainingDTO.getIdPeriodization())
                .orElseThrow(() -> new IllegalArgumentException("Id periodização inválido"));

        Training newTraining = Training
                .builder()
                .trainingName(registerTrainingDTO.getTrainingName())
                .trainingType(TrainingType.fromValue(registerTrainingDTO.getTrainingType()))
                .createdAt(LocalDateTime.now())
                .isDone(false)
                .periodization(periodization)
                .build();

        Training training = trainingRepository.save(newTraining);

        return RecoveryTrainingDTO
                .builder()
                .trainingType(training.getTrainingType().name())
                .trainingName(training.getTrainingName())
                .createdAt(DateUtils.formatDate(training.getCreatedAt()))
                .build();
    }

    public RecoveryTrainingDTO updateTraining(Long id, RegisterTrainingDTO registerTrainingDTO) {
        Training training = trainingRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Treino não encontrado"));

        Periodization periodization = periodizationRepository
                .findById(registerTrainingDTO.getIdPeriodization())
                .orElseThrow(() -> new IllegalArgumentException("Periodização não encontrado"));

        training.setTrainingName(registerTrainingDTO.getTrainingName());
        training.setTrainingType(TrainingType.fromValue(registerTrainingDTO.getTrainingType()));
        training.setPeriodization(periodization);

        Training updateTraining = trainingRepository.save(training);

        return RecoveryTrainingDTO
                .builder()
                .trainingType(updateTraining.getTrainingType().name())
                .trainingName(updateTraining.getTrainingName())
                .createdAt(DateUtils.formatDate(updateTraining.getCreatedAt()))
                .updatedAt(DateUtils.formatDate(LocalDateTime.now()))
                .build();
    }

    @Transactional
    public void deleteTraining(Long id) {
        trainingRepository
                .findById(id)
                .map(training -> {
                            trainingRepository.delete(training);
                            return Void.class;
                        }
                )
                .orElseThrow(() -> new IllegalArgumentException("Treino não encontrado"));
    }

    @Transactional(readOnly = true)
    public RecoveryTrainingDTO getTrainingById(Long id) {
        Training training = trainingRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Treino não encontrado"));

        return RecoveryTrainingDTO
                .builder()
                .trainingName(training.getTrainingName())
                .trainingType(training.getTrainingType().name())
                .createdAt(DateUtils.formatDate(training.getCreatedAt()))
                .updatedAt(DateUtils.checkUpdateDate(training.getUpdatedAt()))
                .build();
    }

    @Transactional(readOnly = true)
    public List<RecoveryTrainingDTO> getAllTrainingByIdPeriodization(Long id) {
        periodizationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Periodização não encontrada"));

        List<Training> trainings = trainingRepository.getAllTrainingByIdPeriodization(id);

        return trainings.stream()
                .map(training -> {
                    return
                            RecoveryTrainingDTO
                                    .builder()
                                    .idTraining(training.getId())
                                    .trainingType(training.getTrainingType().name())
                                    .trainingName(training.getTrainingName())
                                    .createdAt(DateUtils.formatDate(training.getCreatedAt()))
                                    .build();
                })
                .collect(Collectors.toList());
    }
}
