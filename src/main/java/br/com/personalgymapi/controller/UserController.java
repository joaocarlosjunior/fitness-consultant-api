package br.com.personalgymapi.controller;

import br.com.personalgymapi.dto.user.RecoveryUserDTO;
import br.com.personalgymapi.dto.user.RegisterUserDTO;
import br.com.personalgymapi.dto.user.UpdateUserDTO;
import br.com.personalgymapi.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //TODOS
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public RecoveryUserDTO createUser(@RequestBody @Valid RegisterUserDTO registerUserDTO){
        return userService.addUser(registerUserDTO);
    }

    //USER e ADMIN
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryUserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    //USER e ADMIN
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletedById(@PathVariable Long id){
        userService.deletedById(id);
    }

    //USER e ADMIN
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryUserDTO updateById(@PathVariable Long id, @RequestBody @Valid UpdateUserDTO updateUserDTO){
        return userService.updateUser(id, updateUserDTO);
    }

    //USER e ADMIN
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RecoveryUserDTO> getAllUsers()  {
        return userService.getAllUsers();
    }

    @PostMapping("/active-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void setActiveUser(@PathVariable Long id){
        userService.setActiveUser(id);
    }

    @PostMapping("/disable-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void setDisableUser(@PathVariable Long id){
        userService.setDisableUser(id);
    }
}
