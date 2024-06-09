package br.com.fitnessconsultant.service.periodization.impl;

import br.com.fitnessconsultant.domain.entities.Periodization;
import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.repository.PeriodizationRepository;
import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.periodization.RecoveryPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.RegisterPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.exception.UserNotFoundException;
import br.com.fitnessconsultant.service.periodization.PeriodizationService;
import br.com.fitnessconsultant.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PeriodizationServiceImpl implements PeriodizationService {
    private final PeriodizationRepository periodizationRepository;
    private final UserRepository userRepository;

    @Transactional
    public RecoveryPeriodizationDTO createPeriodization(RegisterPeriodizationDTO registerPeriodization) {
        User user = userRepository
                .findById(registerPeriodization.getIdUser())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        Periodization periodization =
                Periodization.builder()
                        .name(registerPeriodization.getName())
                        .numberWeeks(registerPeriodization.getNumberWeeks())
                        .createdAt(LocalDateTime.now())
                        .user(user)
                        .build();

        Periodization newPeriodization = periodizationRepository.save(periodization);

        return RecoveryPeriodizationDTO
                .builder()
                .idPeriodization(newPeriodization.getId())
                .name(newPeriodization.getName())
                .numberWeeks(newPeriodization.getNumberWeeks())
                .createdAt(DateUtils.formatDate(newPeriodization.getCreatedAt()))
                .startDate(DateUtils.checkUpdateDate(newPeriodization.getStarDate()))
                .build();
    }

    @Transactional
    public RecoveryPeriodizationDTO updatePeriodization(Long id, UpdatePeriodizationDTO updatePeriodizationDTO) {
        Periodization periodization = periodizationRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Periodização não encontrado"));

        periodization.setName(updatePeriodizationDTO.getName());
        periodization.setNumberWeeks(updatePeriodizationDTO.getNumberWeeks());
        periodization.setUpdatedAt(LocalDateTime.now());

        Periodization updatePeriodization = periodizationRepository.save(periodization);

        return RecoveryPeriodizationDTO.builder()
                .name(updatePeriodizationDTO.getName())
                .numberWeeks(updatePeriodization.getNumberWeeks())
                .createdAt(DateUtils.formatDate(updatePeriodization.getCreatedAt()))
                .updatedAt(DateUtils.formatDate(periodization.getUpdatedAt()))
                .build();
    }

    @Transactional(readOnly = true)
    public RecoveryPeriodizationDTO getPeriodizationById(Long id) {
        Periodization periodization = periodizationRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Periodização não encontrado"));

        return RecoveryPeriodizationDTO
                .builder()
                .idPeriodization(periodization.getId())
                .name(periodization.getName())
                .numberWeeks(periodization.getNumberWeeks())
                .startDate(DateUtils.checkUpdateDate(periodization.getStarDate()))
                .createdAt(DateUtils.formatDate(periodization.getCreatedAt()))
                .build();
    }

    @Transactional
    public void deletePeriodization(Long id) {
        periodizationRepository
                .findById(id)
                .map(training -> {
                            periodizationRepository.delete(training);
                            return Void.class;
                        }
                )
                .orElseThrow(() -> new RecordNotFoundException("Periodização não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<RecoveryPeriodizationDTO> getAllPeriodization() {
        return periodizationRepository
                .findAll()
                .stream()
                .map((periodization -> RecoveryPeriodizationDTO
                        .builder()
                        .idPeriodization(periodization.getId())
                        .name(periodization.getName())
                        .numberWeeks(periodization.getNumberWeeks())
                        .startDate(DateUtils.checkUpdateDate(periodization.getStarDate()))
                        .createdAt(DateUtils.formatDate(periodization.getCreatedAt()))
                        .updatedAt(DateUtils.checkUpdateDate(periodization.getUpdatedAt()))
                        .build()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RecoveryPeriodizationDTO> getAllPeriodizationByUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        List<Periodization> periodizations = periodizationRepository
                .getAllPeriodizationByIdUser(id);

        return periodizations.
                stream()
                .map(periodization -> RecoveryPeriodizationDTO
                        .builder()
                        .idPeriodization(periodization.getId())
                        .name(periodization.getName())
                        .numberWeeks(periodization.getNumberWeeks())
                        .createdAt(DateUtils.formatDate(periodization.getCreatedAt()))
                        .build())
                .collect(Collectors.toList());
    }
}
