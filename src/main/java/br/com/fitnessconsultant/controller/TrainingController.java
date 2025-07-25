package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.dto.training.RequestTrainingDTO;
import br.com.fitnessconsultant.dto.training.RequestUpdateTrainingDTO;
import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;
import br.com.fitnessconsultant.exception.ApiErrors;
import br.com.fitnessconsultant.service.training.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@RequestMapping("/api/v1/workouts")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Operation(
            summary = "Cria treino",
            description = "Cria um treino. A resposta é um Training com id do treino, id da periodização," +
                    "  tipo do treino, nome do treino, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado um novo treino", content = { @Content(schema = @Schema(implementation = ResponseTrainingDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseTrainingDTO> create(
            @RequestBody @NotNull @Validated RequestTrainingDTO requestTrainingDTO
    ) {
        return trainingService.create(requestTrainingDTO);
    }

    @Operation(
            summary = "Atualiza treino pelo Id",
            description = "Atualiza treino. A resposta é um Training com id do treino, id da periodização," +
                    "  tipo do treino, nome do treino, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponseTrainingDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @Parameters({
            @Parameter(name = "id", description = "Atualiza treino pelo Id")
    })
    @PutMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseTrainingDTO> update(
            @PathVariable @Positive @NotNull Long id,
            @RequestBody @NotNull @Validated RequestUpdateTrainingDTO requestUpdateTrainingDTO
    ) {
        return trainingService.update(id, requestUpdateTrainingDTO);
    }

    @Operation(
            summary = "Deleta treino pelo Id",
            description = "Deleta um treino pelo Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Treino deletado com sucesso"),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @Parameters({
            @Parameter(name = "id", description = "Deleta treino pelo Id")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id){
        trainingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Recupera treino pelo Id",
            description = "Obtem um treino pelo seu Id. A resposta é um Training com id do treino, id da periodização," +
                    "  tipo do treino, nome do treino, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponseTrainingDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @Parameters({
            @Parameter(name = "id", description = "Retorna treino pelo Id")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ResponseTrainingDTO> findById(@PathVariable @Positive @NotNull Long id){
        return trainingService.findById(id);
    }

    @Operation(
            summary = "Recupera todos os treinos pelo Id da periodização",
            description = "Obtem todos os treino pelo Id da periodização. A resposta é um array de Training com id do treino, id da periodização," +
                    "  tipo do treino, nome do treino, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponseTrainingDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @Parameters({
            @Parameter(name = "id", description = "Retorna todos os treinos pelo Id de Periodização")
    })
    @GetMapping("/periodization/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResponseTrainingDTO>> getAllTrainingByIdPeriodization(@PathVariable @Positive @NotNull Long id){
        return trainingService.getAllTrainingByIdPeriodization(id);
    }

}
