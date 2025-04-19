package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.UserController;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserPeriodizationInfoDTO;
import br.com.fitnessconsultant.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerImpl implements UserController {
    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ResponseUserDTO> findById(@PathVariable @Positive @NotNull Long id) {
        return userService.findById(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id) {
        userService.setDisableUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ResponseUserDTO> update(
            @PathVariable @Positive @NotNull Long id,
            @RequestBody @Valid @NotNull @Validated UpdateUserDTO updateUserDTO) {
        return userService.update(id, updateUserDTO);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResponseUserDTO>> list() {
        return userService.list();
    }

    @Override
    @PatchMapping("/active-user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Map<String, String>> setActiveUser(
            @PathVariable @Positive @NotNull Long id
    ) {
        return userService.setActiveUser(id);
    }

    @Override
    @PatchMapping("/disable-user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Map<String, String>> setDisableUser(@PathVariable @Positive @NotNull Long id) {
        return userService.setDisableUser(id);
    }

    @Override
    @GetMapping("/user/{id}/workouts")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<UserPeriodizationInfoDTO>> getAllUserTrainingInfo(@PathVariable Long id){
        return userService.getAllUserTraining(id);
    }
}
