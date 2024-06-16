package br.com.fitnessconsultant.service.user.impl;

import br.com.fitnessconsultant.domain.entities.Periodization;
import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;
import br.com.fitnessconsultant.exception.InfoAlreadyExistsException;
import br.com.fitnessconsultant.exception.UserNotFoundException;
import br.com.fitnessconsultant.mappers.UserMapper;
import br.com.fitnessconsultant.service.user.UserService;
import br.com.fitnessconsultant.utils.DateUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public ResponseUserDTO create(@Valid @NotNull RequestUserDTO requestUserDTO) {

        boolean emailExists = userRepository
                .existsByEmailIgnoreCase(requestUserDTO.getEmail());

        if(emailExists){
            throw new InfoAlreadyExistsException("Email já cadastrado");
        }

        boolean phoneExists = userRepository
                .existsByPhone(requestUserDTO.getPhone());

        if(phoneExists){
            throw new InfoAlreadyExistsException("Telefone já cadastrado");
        }

        return userMapper.toDto(userRepository.save(userMapper.toEntity(requestUserDTO)));
    }

    public ResponseUserDTO findById(@NotNull @Positive Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));

        return userMapper.toDto(user);
    }

    @Transactional
    public void delete(@NotNull @Positive Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        userRepository.deletedById(id);
    }

    @Transactional
    public void setActiveUser(@NotNull @Positive Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));

        if(!user.isActive()){
            user.setActive(true);
            userRepository.save(user);
        }
    }

    @Transactional
    public void setDisableUser(@NotNull @Positive Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));

        if(user.isActive()){
            user.setActive(false);
            userRepository.save(user);
        }
    }

    @Transactional
    public ResponseUserDTO update(@NotNull @Positive Long id, @NotNull @Valid UpdateUserDTO updateUserDTO) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));

        String currentEmail = user.getEmail();
        String newEmail = updateUserDTO.getEmail();

        if(!newEmail.equals(currentEmail)){
            boolean emailExists = userRepository
                    .existsByEmailIgnoreCase(newEmail);

            if(emailExists){
                throw new InfoAlreadyExistsException("Email já cadastrado");
            }
        }

        String newPhone = updateUserDTO.getPhone();
        String currentPhone = user.getPhone();

        if (!newPhone.equals(currentPhone)) {
            boolean phoneExists = userRepository
                    .existsByPhone(newPhone);

            if(phoneExists){
                throw new InfoAlreadyExistsException("Telefone já cadastrado");
            }
        }

        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setEmail(updateUserDTO.getEmail());
        user.setPhone(updateUserDTO.getPhone());

        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<ResponseUserDTO> list() {
        return userRepository
                .findAllUserIsActive(true)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    private List<ResponsePeriodizationDTO> getAllPeriodization(Set<Periodization> periodizations) {
        if(periodizations.isEmpty()){
            System.out.println("Teste");
            return null;
        }

        return periodizations
                .stream()
                .map(periodization -> ResponsePeriodizationDTO
                        .builder()
                        .idPeriodization(periodization.getId())
                        .name(periodization.getName())
                        .numberWeeks(periodization.getNumberWeeks())
                        .startDate(DateUtils.checkUpdateDate(periodization.getStarDate()))
                        .createdAt(DateUtils.formatDate(periodization.getCreatedAt()))
                        .updatedAt(DateUtils.checkUpdateDate(periodization.getUpdatedAt()))
                        .build())
                .collect(Collectors.toList());
    }
}
