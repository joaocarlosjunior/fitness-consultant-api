package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.dto.exercise.RequestExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.ResponseExerciseDTO;
import br.com.fitnessconsultant.exception.ApiErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Exercício", description = "APIs de Gerenciamento de exercícios")
public interface ExerciseController {

    @Operation(
            summary = "Adiciona exercício",
            description = "Adiciona exercício. A resposta é um Exercise com id, nº series, nº repetições, " +
                    "carga inicial, carga final, método e nome do exercício"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Adicionado um novo exercício com sucesso", content = { @Content(schema = @Schema(implementation = ResponseExerciseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Id Nome de Exercício ou Treino inválido ou inexistente",
                    content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    ResponseEntity<ResponseExerciseDTO> create(@RequestBody @NotNull RequestExerciseDTO requestExerciseDTO);

    @Operation(
            summary = "Atualiza exercício pelo Id",
            description = "Atualiza exercício pelo Id. A resposta é um Exercise com id, nº series, nº repetições, " +
                    "carga inicial, carga final, método e nome do exercício"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atualizado exercício com sucesso",
                    content = { @Content(schema = @Schema(implementation = ResponseExerciseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Id Exercício, Nome de Exercício ou Treino inválido ou inexistente",
                    content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @Parameters({
            @Parameter(name = "id", description = "Atualiza Exercício pelo Id")
    })
    ResponseEntity<ResponseExerciseDTO> update(@PathVariable @Positive @NotNull Long id,@RequestBody @NotNull RequestExerciseDTO requestExerciseDTO);

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
    @Parameters({
            @Parameter(name = "id", description = "Deleta Exercício pelo Id")
    })
    ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id);

    @Operation(
            summary = "Recupera todos exercícios pelo Id de treino",
            description = "Obtem todos exercícios pelo Id de treino. A resposta é um array de Exercise com id, nº series, nº repetições, " +
                    "carga inicial, carga final, método e nome do exercício"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retornado com sucesso todos exercícios pelo id de treino"
                    , content = { @Content(schema = @Schema(implementation = ResponseExerciseDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Id treino inválido ou inexistente",
                    content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @Parameters({
            @Parameter(name = "id", description = "Retorna todos os Exercícios pelo Id do Treino")
    })
    ResponseEntity<List<ResponseExerciseDTO>> getAllExercisesByIdTraining(@PathVariable @Positive @NotNull Long id);

}
