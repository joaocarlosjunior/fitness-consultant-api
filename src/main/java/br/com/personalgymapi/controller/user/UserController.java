package br.com.personalgymapi.controller.user;

import br.com.personalgymapi.dto.user.RecoveryUserDTO;
import br.com.personalgymapi.dto.user.RegisterUserDTO;
import br.com.personalgymapi.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

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
    @ResponseStatus(HttpStatus.OK)
    public void deletedById(@PathVariable Long id){
        userService.deletedById(id);
    }

    //USER e ADMIN
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateById(@PathVariable Long id, @RequestBody RegisterUserDTO registerUserDTO){
        userService.update(id, registerUserDTO);
    }

    //USER e ADMIN
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RecoveryUserDTO> getAllUsers()  {
        return userService.getAllUsers();
    }
}
