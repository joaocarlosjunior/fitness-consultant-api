package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.UserController;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;
import br.com.fitnessconsultant.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerImpl implements UserController {
    private final UserService userService;

    public UserControllerImpl(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseUserDTO findById(@PathVariable @Positive @NotNull Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public void delete(@PathVariable @Positive @NotNull Long id){
        userService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseUserDTO update(@PathVariable @Positive @NotNull Long id,
                                  @RequestBody @Valid @NotNull UpdateUserDTO updateUserDTO){
        return userService.update(id, updateUserDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")

    public List<ResponseUserDTO> list()  {
        return userService.list();
    }

    @PatchMapping("/active-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public void setActiveUser(@PathVariable @Positive @NotNull Long id){
        userService.setActiveUser(id);
    }

    @PatchMapping("/disable-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public void setDisableUser(@PathVariable @Positive @NotNull Long id){
        userService.setDisableUser(id);
    }
}
