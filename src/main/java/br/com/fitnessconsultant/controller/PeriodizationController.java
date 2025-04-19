package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;
import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;
import br.com.fitnessconsultant.exception.ApiErrors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Periodização", description = "APIs de Gerenciamento de Periodização")
public interface PeriodizationController {

    @Operation(
            summary = "Cria periodização",
            description = "Cria uma periodização. A resposta é um objeto Periodization com id, nome da periodização, número de semanas," +
                    "  data de início, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Periodização criada com sucesso",content = { @Content(schema = @Schema(implementation = ResponseTrainingDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    ResponseEntity<ResponsePeriodizationDTO> create(RequestPeriodizationDTO requestPeriodizationDTO);

    @Operation(
            summary = "Retorna periodização pelo Id",
            description = "Obtem uma periodização pelo seu Id. A resposta é um objeto Periodization com id, nome da periodização, número de semanas," +
                    "  data de início, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponsePeriodizationDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @Parameters({
            @Parameter(name = "id", description = "Retorna Periodização pelo Id")
    })
    ResponseEntity<ResponsePeriodizationDTO> findById(Long id);

    @Operation(
            summary = "Atualiza periodização pelo Id",
            description = "Atualiza periodização pelo Id. A resposta é um objeto Periodization com id, nome da periodização, número de semanas," +
                    "  data de início, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponsePeriodizationDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @Parameters({
            @Parameter(name = "id", description = "Atualiza Periodização pelo Id")
    })
    ResponseEntity<ResponsePeriodizationDTO> update(Long id,
                                    UpdatePeriodizationDTO updatePeriodizationDTO);

    @Operation(
            summary = "Deleta periodização pelo Id",
            description = "Deleta periodização pelo Id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Periodização deletada com sucesso"),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @Parameters({
            @Parameter(name = "id", description = "Deleta Periodização pelo Id")
    })
    ResponseEntity<Void> delete(Long id);

    @Operation(
            summary = "Retorna todas as periodizações",
            description = "Obtem todas as periodizações cadastradss na base de dados. A resposta é um array de objeto Periodization com id, nome da periodização, número de semanas," +
                    "  data de início, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponsePeriodizationDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    ResponseEntity<List<ResponsePeriodizationDTO>> list();

    @Operation(
            summary = "Retorna todas as periodizações pelo Id do usuário",
            description = "Obtem todas as periodizações pelo Id do usuário. A resposta é um array de objeto Periodization com id, nome da periodização, número de semanas," +
                    "  data de início, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponsePeriodizationDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @Parameters({
            @Parameter(name = "id", description = "Retorna todos os Periodização pelo Id do Usuário")
    })
    ResponseEntity<List<ResponsePeriodizationDTO>> getAllPeriodizationByIdUser(Long id);
}
