package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.dto.exercisename.RequestExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.RequestUpdateExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.ResponseExerciseNameDTO;
import br.com.fitnessconsultant.exception.ApiErrors;
import br.com.fitnessconsultant.service.exercisename.ExerciseNameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/exercise-name")
public class ExerciseNameController {

    private final ExerciseNameService exerciseNameService;

    public ExerciseNameController(ExerciseNameService exerciseNameService) {
        this.exerciseNameService = exerciseNameService;
    }

    @Operation(
            summary = "Adiciona nome de exercício",
            description = "Adiciona nome de exercício"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado um novo nome de exercício"),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Id do Grupo Muscular inválido ou inexistente", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @NotNull @Validated RequestExerciseNameDTO requestExerciseNameDTO) {
        exerciseNameService.create(requestExerciseNameDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Recupera nome de exercício pelo Id",
            description = "Obtem nome de exercício pelo Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Criado um novo nome de exercício",
                    content = {@Content(schema = @Schema(implementation = ResponseExerciseNameDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Id Nome Exercício inválido ou inexistente", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    @Parameters({
            @Parameter(name = "id", description = "Retorna Nome Exercício pelo Id")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseExerciseNameDTO> findById(@PathVariable @Positive @NotNull Long id) {
        return ResponseEntity.ok(exerciseNameService.findById(id));
    }

    @Operation(
            summary = "Atualiza nome de exercício pelo Id",
            description = "Atualiza nome de exercício pelo Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado nome de exercício com sucesso",
                    content = {@Content(schema = @Schema(implementation = ResponseExerciseNameDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Id Nome Exercício ou Grupo Muscular inválido ou inexistente", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    @Parameters({
            @Parameter(name = "id", description = "Altera Nome Exercício pelo Id")
    })
    @PutMapping({"/{id}"})
    public ResponseEntity<ResponseExerciseNameDTO> update(
            @PathVariable @Positive @NotNull Long id,
            @RequestBody @NotNull @Validated RequestUpdateExerciseNameDTO requestUpdateExerciseNameDTO
    ) {
        return ResponseEntity.ok(exerciseNameService.update(id, requestUpdateExerciseNameDTO));
    }

    @Operation(
            summary = "Deleta nome de exercício pelo Id",
            description = "Deleta nome de exercício pelo Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deletado nome de exercício com sucesso"),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Id Nome Exercício inválido ou inexistente", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    @Parameters({
            @Parameter(name = "id", description = "Deleta Nome Exercício pelo Id")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id) {
        exerciseNameService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Recuperar todos os nomes de exercícios",
            description = "Obtem todos os nomes de exercícios. A resposta é um array de Nome de Exercício com id, nome de exercicio e grupo muscular"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retornado todos nomes de exercício com sucesso",
                    content = {@Content(schema = @Schema(implementation = ResponseExerciseNameDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    @GetMapping
    public ResponseEntity<List<ResponseExerciseNameDTO>> list() {
        return ResponseEntity.ok(exerciseNameService.list());
    }

}
