package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;
import br.com.fitnessconsultant.exception.ApiErrors;
import br.com.fitnessconsultant.service.periodization.PeriodizationService;
import br.com.fitnessconsultant.validation.CheckUserAccess;
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
@RequestMapping("/api/v1/periodizations")
public class PeriodizationController {

    private final PeriodizationService periodizationService;

    public PeriodizationController(PeriodizationService periodizationService){
        this.periodizationService = periodizationService;
    }

    @Operation(
            summary = "Cria periodização",
            description = "Cria uma periodização. A resposta é um objeto Periodization com id, nome da periodização, número de semanas," +
                    "  data de início, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Periodização criada com sucesso",content = { @Content(schema = @Schema(implementation = ResponsePeriodizationDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponsePeriodizationDTO> create(@RequestBody @NotNull @Validated RequestPeriodizationDTO requestPeriodizationDTO) {
        return periodizationService.create(requestPeriodizationDTO);
    }

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
    @GetMapping("/{id}")
    public ResponseEntity<ResponsePeriodizationDTO> findById(@PathVariable @Positive @NotNull Long id){
        System.out.println("id: " + id);
        return periodizationService.findById(id);
    }

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
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponsePeriodizationDTO> update(
            @PathVariable @Positive @NotNull Long id,
            @RequestBody @NotNull @Validated UpdatePeriodizationDTO updatePeriodizationDTO){
        return periodizationService.update(id, updatePeriodizationDTO);
    }

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
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id){
        periodizationService.delete(id);
        return ResponseEntity.noContent().build();
    }

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
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResponsePeriodizationDTO>> list(){
        return periodizationService.list();
    }

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
    @GetMapping("/user/{id}")
    @CheckUserAccess
    public ResponseEntity<List<ResponsePeriodizationDTO>> getAllPeriodizationByIdUser(@PathVariable @Positive @NotNull Long id){
        return periodizationService.getAllPeriodizationByUser(id);
    }
}
