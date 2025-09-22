package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;
import br.com.fitnessconsultant.dto.user.usertraininginfo.UserPeriodizationInfoDTO;
import br.com.fitnessconsultant.exception.ApiErrors;
import br.com.fitnessconsultant.service.user.UserService;
import br.com.fitnessconsultant.validation.CheckUserAccess;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Recupera usuário pelo Id",
            description = "Obtem usuário pelo seu Id . A resposta é um objeto User com id, nome, sobrenome," +
                    "  email, telefone, role, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponseUserDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @GetMapping("/{id}")
    @CheckUserAccess
    public ResponseEntity<ResponseUserDTO> findById(@PathVariable @Positive @NotNull Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(
            summary = "Deleta usuário pelo Id",
            description = "Deleta usuário pelo seu Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @DeleteMapping("/{id}")
    @CheckUserAccess
    public ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id) {
        userService.setDisableUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Atualiza usuário pelo Id",
            description = "Atualiza usuário pelo seu Id . A resposta é um objeto User com id, nome, sobrenome," +
                    "  email, telefone, role, " +
                    "data de criação e atualização."

    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponseUserDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PutMapping("/{id}")
    @CheckUserAccess
    public ResponseEntity<ResponseUserDTO> update(
            @PathVariable @Positive @NotNull Long id,
            @RequestBody @NotNull @Validated UpdateUserDTO updateUserDTO) {
        return ResponseEntity.ok(userService.update(id, updateUserDTO));
    }

    @Operation(
            summary = "Retorna todos os usuários ativos",
            description = "Obtem todos os usuários ativos . A resposta é um array de objeto User com id, nome, sobrenome," +
                    "  email, telefone, role, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponseUserDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResponseUserDTO>> list() {
        return ResponseEntity.ok(userService.list());
    }

    @Operation(
            summary = "Ativa usuário pelo Id",
            description = "Ativa um usuário pelo seu Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário ativado com sucesso"),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PatchMapping("/active-user/{id}")
    @CheckUserAccess
    public ResponseEntity<Map<String, String>> setActiveUser(
            @PathVariable @Positive @NotNull Long id
    ) {
        Map<String, String> response = new HashMap<>();
        if(userService.setActiveUser(id)){
            response.put("message", "Usuário ativado com sucesso");
            return ResponseEntity.ok(response);
        }else{
            response.put("message", "Usuário já ativo");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @Operation(
            summary = "Desabilita usuário pelo Id",
            description = "Desabilita um usuário pelo seu Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário desabilitado com sucesso"),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PatchMapping("/disable-user/{id}")
    @CheckUserAccess
    public ResponseEntity<Map<String, String>> setDisableUser(@PathVariable @Positive @NotNull Long id) {
        Map<String, String> response = new HashMap<>();
        if(userService.setDisableUser(id)){
            response.put("message", "Usuário desativado com sucesso");;
            return ResponseEntity.ok(response);
        }else{
            response.put("message", "Usuário já não está ativo");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @Operation(
            summary = "Busca por todos os treinos pelo Id do usuário",
            description = "Busca por todos os treinos pelo Id do usuário"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retornado todos os treinos com sucesso"),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @GetMapping("/user/{id}/workouts")
    @CheckUserAccess
    public ResponseEntity<List<UserPeriodizationInfoDTO>> getAllUserTrainingInfo(@PathVariable @NotNull @Positive Long id){
        return ResponseEntity.ok(userService.getAllUserTraining(id));
    }
}
