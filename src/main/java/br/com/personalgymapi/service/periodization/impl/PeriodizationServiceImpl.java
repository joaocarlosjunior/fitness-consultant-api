package br.com.personalgymapi.service.periodization.impl;

import br.com.personalgymapi.domain.entities.Periodization;
import br.com.personalgymapi.domain.entities.User;
import br.com.personalgymapi.domain.repository.PeriodizationRepository;
import br.com.personalgymapi.domain.repository.UserRepository;
import br.com.personalgymapi.dto.periodization.RecoveryPeriodizationDTO;
import br.com.personalgymapi.dto.periodization.RegisterPeriodizationDTO;
import br.com.personalgymapi.dto.periodization.UpdatePeriodizationDTO;
import br.com.personalgymapi.exception.UserNotFoundException;
import br.com.personalgymapi.service.periodization.PeriodizationService;
import br.com.personalgymapi.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                        .user(user)
                        .build();

        Periodization newPeriodization = periodizationRepository.save(periodization);

        return RecoveryPeriodizationDTO
                .builder()
                .idPeriodization(newPeriodization.getId())
                .idUser(newPeriodization.getUser().getId())
                .name(newPeriodization.getName())
                .numberWeeks(newPeriodization.getNumberWeeks())
                .startDate(DateUtils.checkUpdateDate(newPeriodization.getStarDate()))
                .build();
    }

    @Transactional
    public RecoveryPeriodizationDTO updatePeriodization(Long id, UpdatePeriodizationDTO updatePeriodizationDTO) {
        Periodization periodization = periodizationRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Periodização não encontrada"));

        periodization.setName(updatePeriodizationDTO.getName());
        periodization.setNumberWeeks(updatePeriodizationDTO.getNumberWeeks());

        Periodization updatePeriodization = periodizationRepository.save(periodization);

        return RecoveryPeriodizationDTO.builder()
                .name(updatePeriodizationDTO.getName())
                .numberWeeks(updatePeriodization.getNumberWeeks())
                .build();
    }

    @Transactional(readOnly = true)
    public RecoveryPeriodizationDTO getPeriodizationById(Long id) {
        Periodization periodization = periodizationRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Periodização não encontrada"));

        return RecoveryPeriodizationDTO
                .builder()
                .idPeriodization(periodization.getId())
                .idUser(periodization.getUser().getId())
                .name(periodization.getName())
                .numberWeeks(periodization.getNumberWeeks())
                .startDate(DateUtils.checkUpdateDate(periodization.getStarDate()))
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
                .orElseThrow(() -> new IllegalArgumentException("Periodização não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<RecoveryPeriodizationDTO> getAllPeriodization() {
        return periodizationRepository
                .findAll()
                .stream()
                .map((periodization -> {
                    return RecoveryPeriodizationDTO
                            .builder()
                            .idPeriodization(periodization.getId())
                            .idUser(periodization.getUser().getId())
                            .name(periodization.getName())
                            .numberWeeks(periodization.getNumberWeeks())
                            .startDate(DateUtils.checkUpdateDate(periodization.getStarDate()))
                            .build();
                })).collect(Collectors.toList());
    }

}
