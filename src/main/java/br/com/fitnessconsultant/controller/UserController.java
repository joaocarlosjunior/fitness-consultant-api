package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.dto.user.RecoveryUserDTO;
import br.com.fitnessconsultant.dto.user.RegisterUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;
import br.com.fitnessconsultant.exception.ApiErrors;
import br.com.fitnessconsultant.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuário", description = "APIs de Gerenciamento de Usuários")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(
            summary = "Cria usuário",
            description = "Cria um usuário. A resposta é um objeto User com id, nome, sobrenome," +
                    "  email, telefone, role, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado um novo usuário", content = { @Content(schema = @Schema(implementation = RecoveryUserDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public RecoveryUserDTO createUser(@RequestBody @Valid RegisterUserDTO registerUserDTO){
        return userService.addUser(registerUserDTO);
    }

    @Operation(
            summary = "Recupera um usuário pelo Id",
            description = "Obtem um usuário pelo seu Id . A resposta é um objeto User com id, nome, sobrenome," +
                    "  email, telefone, role, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RecoveryUserDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryUserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(
            summary = "Deleta um usuário pelo Id",
            description = "Deleta um usuário pelo seu Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletedById(@PathVariable Long id){
        userService.deletedById(id);
    }

    @Operation(
            summary = "Atualiza um usuário pelo Id",
            description = "Atualiza um usuário pelo seu Id . A resposta é um objeto User com id, nome, sobrenome," +
                    "  email, telefone, role, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RecoveryUserDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryUserDTO updateById(@PathVariable Long id, @RequestBody @Valid UpdateUserDTO updateUserDTO){
        return userService.updateUser(id, updateUserDTO);
    }

    @Operation(
            summary = "Retorna todos os usuários",
            description = "Obtem todos os usuários cadastrados na base de dados. A resposta é um array de objeto User com id, nome, sobrenome," +
                    "  email, telefone, role, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RecoveryUserDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RecoveryUserDTO> getAllUsers()  {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Ativa usuário pelo Id",
            description = "Ativa um usuário pelo seu Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário ativado com sucesso"),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PatchMapping("/active-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void setActiveUser(@PathVariable Long id){
        userService.setActiveUser(id);
    }

    @Operation(
            summary = "Desabilita usuário pelo Id",
            description = "Desabilita um usuário pelo seu Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário desabilitado com sucesso"),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PatchMapping("/disable-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void setDisableUser(@PathVariable Long id){
        userService.setDisableUser(id);
    }
}
