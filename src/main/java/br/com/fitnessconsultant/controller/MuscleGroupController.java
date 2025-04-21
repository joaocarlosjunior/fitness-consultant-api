package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.dto.musuculegroup.RequestMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.ResponseMuscleGroupDTO;
import br.com.fitnessconsultant.exception.ApiErrors;
import br.com.fitnessconsultant.service.musclegroup.MuscleGroupService;
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
@RequestMapping("/api/v1/muscle-groups")
public class MuscleGroupController {
    private final MuscleGroupService muscleGroupService;

    public MuscleGroupController(MuscleGroupService muscleGroupService) {
        this.muscleGroupService = muscleGroupService;
    }

    @Operation(
            summary = "Cria grupo musuclar",
            description = "Cria um grupo musuclar."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado um novo grupo muscular"),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @NotNull @Validated RequestMuscleGroupDTO requestMuscleGroupDTO) {
        muscleGroupService.create(requestMuscleGroupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Deleta um grupo muscular pelo Id",
            description = "Deleta um grupo muscular pelo seu Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Grupo muscular deletado com sucesso"),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    @Parameters({
            @Parameter(name = "id", description = "Deleta Grupo Muscular pelo Id")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id) {
        muscleGroupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Atualiza grupo muscular pelo Id",
            description = "Atualiza grupo muscular pelo seu Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grupo muscular atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = ResponseMuscleGroupDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Id grupo muscular inválido ou não existe", content = {@Content(schema = @Schema(implementation = ApiErrors.class)
                    , mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    @Parameters({
            @Parameter(name = "id", description = "Atualiza Grupo Muscular pelo Id")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMuscleGroupDTO> update(
            @PathVariable @Positive @NotNull Long id,
            @RequestBody @NotNull @Validated RequestMuscleGroupDTO requestMuscleGroupDTO) {
        return muscleGroupService.update(id, requestMuscleGroupDTO);
    }

    @Operation(
            summary = "Recupera grupo muscular pelo Id",
            description = "Obtem um grupo muscular pelo seu Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retornado grupo muscular com sucesso",
                    content = {@Content(schema = @Schema(implementation = ResponseMuscleGroupDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Id grupo muscular inválido ou não existe", content = {@Content(schema = @Schema(implementation = ApiErrors.class)
                    , mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    @Parameters({
            @Parameter(name = "id", description = "Retorna Grupo Muscular pelo Id")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMuscleGroupDTO> findById(@PathVariable @Positive @NotNull Long id) {
        return muscleGroupService.findById(id);
    }

    @Operation(
            summary = "Recupera todos os grupo musculares",
            description = "Obtem todos grupo musculares"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retornado todos Grupos Musculares com sucesso",
                    content = {@Content(schema = @Schema(implementation = ResponseMuscleGroupDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    @GetMapping
    public ResponseEntity<List<ResponseMuscleGroupDTO>> list() {
        return muscleGroupService.list();
    }

}
