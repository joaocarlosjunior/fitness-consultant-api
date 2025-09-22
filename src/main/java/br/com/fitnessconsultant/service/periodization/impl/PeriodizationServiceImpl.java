package br.com.fitnessconsultant.service.periodization.impl;

import br.com.fitnessconsultant.domain.entities.Periodization;
import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.repository.PeriodizationRepository;
import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;
import br.com.fitnessconsultant.exception.RecordNotFoundException;
import br.com.fitnessconsultant.exception.UserNotFoundException;
import br.com.fitnessconsultant.mappers.PeriodizationMapper;
import br.com.fitnessconsultant.service.periodization.PeriodizationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeriodizationServiceImpl implements PeriodizationService {
    private final PeriodizationRepository periodizationRepository;
    private final UserRepository userRepository;
    private final PeriodizationMapper periodizationMapper;

    public PeriodizationServiceImpl(PeriodizationRepository periodizationRepository,
                                    UserRepository userRepository,
                                    PeriodizationMapper periodizationMapper) {
        this.periodizationRepository = periodizationRepository;
        this.userRepository = userRepository;
        this.periodizationMapper = periodizationMapper;
    }

    @Transactional
    public ResponsePeriodizationDTO create(@NotNull @Valid RequestPeriodizationDTO registerPeriodization) {
        User user = userRepository
                .findById(registerPeriodization.idUser()).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        return periodizationMapper
                .toDto(periodizationRepository
                        .save(periodizationMapper.toEntity(registerPeriodization, user)));
    }

    @Transactional
    public ResponsePeriodizationDTO update(@NotNull @Positive Long id, @NotNull @Valid UpdatePeriodizationDTO updatePeriodizationDTO) {
        Periodization periodization = periodizationRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Periodização não encontrado"));

        if (updatePeriodizationDTO.name() != null && !updatePeriodizationDTO.name().isBlank()) {
            periodization.setName(updatePeriodizationDTO.name());
        }

        if (updatePeriodizationDTO.numberWeeks() != null) {
            periodization.setNumberWeeks(updatePeriodizationDTO.numberWeeks());
        }

        if (updatePeriodizationDTO.idUser() != null) {
            User user = userRepository.findById(updatePeriodizationDTO.idUser()).orElseThrow(() -> new UserNotFoundException("Usuario não encontrato"));
            periodization.setUser(user);
        }

        return periodizationMapper.toDto(periodizationRepository.save(periodization));
    }

    @Transactional(readOnly = true)
    public ResponsePeriodizationDTO findById(@NotNull @Positive Long id) {
        Periodization periodization = periodizationRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Periodização não encontrado"));

        return periodizationMapper.toDto(periodizationRepository.save(periodization));
    }

    @Transactional
    public void delete(@NotNull @Positive Long id) {
        periodizationRepository.delete(periodizationRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Periodização não encontrado")));
    }

    @Transactional(readOnly = true)
    public List<ResponsePeriodizationDTO> list() {
        return periodizationRepository
                .findAll()
                .stream()
                .map((periodizationMapper::toDto))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponsePeriodizationDTO> getAllPeriodizationByUser(@NotNull @Positive Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        List<Periodization> periodizations = periodizationRepository
                .getAllPeriodizationByIdUser(id);

        return periodizations.
                stream()
                .map(periodizationMapper::toDto)
                .collect(Collectors.toList());
    }
}
