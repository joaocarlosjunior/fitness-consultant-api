package br.com.fitnessconsultant.service.training.impl;

import br.com.fitnessconsultant.domain.entities.Periodization;
import br.com.fitnessconsultant.domain.entities.Training;
import br.com.fitnessconsultant.domain.enums.TrainingType;
import br.com.fitnessconsultant.domain.repository.PeriodizationRepository;
import br.com.fitnessconsultant.domain.repository.TrainingRepository;
import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;
import br.com.fitnessconsultant.dto.training.RequestTrainingDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.mappers.TrainingMapper;
import br.com.fitnessconsultant.service.training.TrainingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;
    private final PeriodizationRepository periodizationRepository;
    private final TrainingMapper trainingMapper;

    public TrainingServiceImpl(TrainingRepository trainingRepository,
                               PeriodizationRepository periodizationRepository,
                               TrainingMapper trainingMapper){
        this.trainingRepository = trainingRepository;
        this.periodizationRepository = periodizationRepository;
        this.trainingMapper = trainingMapper;
    }

    @Transactional
    public ResponseTrainingDTO create(@Valid @NotNull RequestTrainingDTO requestTrainingDTO) {

        Periodization periodization = periodizationRepository
                .findById(requestTrainingDTO.idPeriodization())
                .orElseThrow(() -> new RecordNotFoundException("Periodização não encontrado"));

        return trainingMapper.toDto(trainingRepository.save(trainingMapper.toEntity(requestTrainingDTO, periodization)));
    }

    @Transactional
    public ResponseTrainingDTO update(@NotNull @Positive Long id, @Valid @NotNull RequestTrainingDTO requestTrainingDTO) {
        Training training = trainingRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Treino não encontrado"));

        Periodization periodization = periodizationRepository
                .findById(requestTrainingDTO.idPeriodization())
                .orElseThrow(() -> new RecordNotFoundException("Periodização não encontrado"));

        training.setTrainingName(requestTrainingDTO.trainingName());
        training.setTrainingType(TrainingType.fromValue(requestTrainingDTO.trainingType()));
        training.setPeriodization(periodization);

        return trainingMapper.toDto(trainingRepository.save(training));
    }

    @Transactional
    public void delete(@NotNull @Positive Long id) {
        trainingRepository.delete(trainingRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Treino não encontrado")));
    }

    @Transactional(readOnly = true)
    public ResponseTrainingDTO findById(@NotNull @Positive Long id) {
        Training training = trainingRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Treino não encontrado"));

        return trainingMapper.toDto(training);
    }

    @Transactional(readOnly = true)
    public List<ResponseTrainingDTO> getAllTrainingByIdPeriodization(Long id) {
        periodizationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Periodização não encontrado"));

        List<Training> trainings = trainingRepository.getAllTrainingByIdPeriodization(id);

        return trainings.stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }
}
