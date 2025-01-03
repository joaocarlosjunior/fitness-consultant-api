package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.dto.musuculegroup.RequestMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.ResponseMuscleGroupDTO;
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

@Tag(name = "Grupo Muscular", description = "APIs de Gerenciamento dos Grupo Musculares")
public interface MuscleGroupController {

    @Operation(
            summary = "Cria grupo musuclar",
            description = "Cria um grupo musuclar."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Criado um novo grupo muscular"),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json")})
    })
    ResponseEntity<Void> create(@RequestBody @NotNull RequestMuscleGroupDTO requestMuscleGroupDTO);

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
    ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id);

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
    ResponseEntity<ResponseMuscleGroupDTO> update(@PathVariable @Positive @NotNull Long id,
                                  @RequestBody @NotNull RequestMuscleGroupDTO requestMuscleGroupDTO);

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
    ResponseEntity<ResponseMuscleGroupDTO> findById(@PathVariable @Positive @NotNull Long id);

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
    ResponseEntity<List<ResponseMuscleGroupDTO>> list();
}
