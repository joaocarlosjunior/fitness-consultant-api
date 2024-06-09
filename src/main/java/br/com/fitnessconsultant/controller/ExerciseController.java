package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.dto.exercise.RecoveryExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.RegisterExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.UpdateIdTrainingExerciseDTO;
import br.com.fitnessconsultant.exception.ApiErrors;
import br.com.fitnessconsultant.service.exercise.ExerciseService;
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

@Tag(name = "Exercício", description = "APIs de Gerenciamento de exercícios")
@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
    }

    @Operation(
            summary = "Adiciona exercício",
            description = "Adiciona exercício. A resposta é um Exercise com id, nº series, nº repetições, " +
                    "carga inicial, carga final, método e nome do exercício"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Adicionado um novo exercício com sucesso", content = { @Content(schema = @Schema(implementation = RecoveryExerciseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Id Nome de Exercício ou Treino inválido ou inexistente",
                    content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecoveryExerciseDTO createExercise(@RequestBody @Valid RegisterExerciseDTO registerExerciseDTO){
        return exerciseService.createExercise(registerExerciseDTO);
    }

    @Operation(
            summary = "Atualiza exercício pelo Id",
            description = "Atualiza exercício pelo Id. A resposta é um Exercise com id, nº series, nº repetições, " +
                    "carga inicial, carga final, método e nome do exercício"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado exercício com sucesso",
                    content = { @Content(schema = @Schema(implementation = RecoveryExerciseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Id Exercício, Nome de Exercício ou Treino inválido ou inexistente",
                    content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryExerciseDTO updateExercise(@PathVariable Long id, @RequestBody RegisterExerciseDTO registerExerciseDTO){
        return exerciseService.updateExercise(id, registerExerciseDTO);
    }

    @Operation(
            summary = "Deletado exercício pelo Id",
            description = "Atualiza exercício pelo Id. A resposta é um Exercise com id, nº series, nº repetições, " +
                    "carga inicial, carga final, método e nome do exercício"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado exercício com sucesso"),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Id Exercício inválido ou inexistente",
                    content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExercise(@PathVariable Long id){
        exerciseService.deleteExercise(id);
    }

    @Operation(
            summary = "Recupera todos exercícios pelo Id de treino",
            description = "Obtem todos exercícios pelo Id de treino. A resposta é um array de Exercise com id, nº series, nº repetições, " +
                    "carga inicial, carga final, método e nome do exercício"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retornado com sucesso todos exercícios pelo id de treino"
                    , content = { @Content(schema = @Schema(implementation = RecoveryExerciseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Id treino inválido ou inexistente",
                    content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @GetMapping("/training/{id}")
    public List<RecoveryExerciseDTO> getAllExercisesByIdTraining(@PathVariable Long id){
        return exerciseService.getAllExercisesByIdTraining(id);
    }
}
