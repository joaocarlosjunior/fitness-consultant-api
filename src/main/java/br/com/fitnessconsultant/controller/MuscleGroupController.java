package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.dto.musuculegroup.RecoveryMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.RegisterMuscleGroupDTO;
import br.com.fitnessconsultant.exception.ApiErrors;
import br.com.fitnessconsultant.service.musclegroup.MuscleGroupService;
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

@Tag(name = "Grupo Muscular", description = "APIs de Gerenciamento dos Grupo Musculares")
@RestController
@RequestMapping("/api/v1/muscle-group")
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
    @ResponseStatus(HttpStatus.CREATED)
    public void createMuscleGroup(@RequestBody @Valid RegisterMuscleGroupDTO registerMuscleGroupDTO) {
        muscleGroupService.addMuscleGroup(registerMuscleGroupDTO);
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
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        muscleGroupService.deletedById(id);
    }

    @Operation(
            summary = "Atualiza grupo muscular pelo Id",
            description = "Atualiza grupo muscular pelo seu Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grupo muscular atualizado com sucesso",
                    content = {@Content(schema = @Schema(implementation = RecoveryMuscleGroupDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Id grupo muscular inválido ou não existe", content = {@Content(schema = @Schema(implementation = ApiErrors.class)
                            , mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryMuscleGroupDTO update(@PathVariable Long id, @RequestBody RegisterMuscleGroupDTO registerMuscleGroupDTO) {
        return muscleGroupService.update(id, registerMuscleGroupDTO);
    }

    @Operation(
            summary = "Recupera grupo muscular pelo Id",
            description = "Obtem um grupo muscular pelo seu Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retornado grupo muscular com sucesso",
                    content = {@Content(schema = @Schema(implementation = RecoveryMuscleGroupDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Id grupo muscular inválido ou não existe", content = {@Content(schema = @Schema(implementation = ApiErrors.class)
                    , mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryMuscleGroupDTO getMuscleGroupById(@PathVariable Long id) {
        return muscleGroupService.getMuscleGroupById(id);
    }

    @Operation(
            summary = "Recupera todos os grupo musculares",
            description = "Obtem todos grupo musculares"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retornado todos Grupos Musculares com sucesso",
                    content = {@Content(schema = @Schema(implementation = RecoveryMuscleGroupDTO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RecoveryMuscleGroupDTO> getAllMuscleGroup() {
        return muscleGroupService.getAllMuscleGroups();
    }


}
