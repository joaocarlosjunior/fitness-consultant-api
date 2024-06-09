package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.dto.periodization.RecoveryPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.RegisterPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;
import br.com.fitnessconsultant.dto.training.RecoveryTrainingDTO;
import br.com.fitnessconsultant.exception.ApiErrors;
import br.com.fitnessconsultant.service.periodization.PeriodizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Periodização", description = "APIs de Gerenciamento de Periodização")
@RestController
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
            @ApiResponse(responseCode = "201", description = "Periodização criada com sucesso",content = { @Content(schema = @Schema(implementation = RecoveryTrainingDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecoveryPeriodizationDTO createPeriodization(@RequestBody @Valid RegisterPeriodizationDTO registerPeriodizationDTO) {
        return periodizationService.createPeriodization(registerPeriodizationDTO);
    }

    @Operation(
            summary = "Retorna periodização pelo Id",
            description = "Obtem uma periodização pelo seu Id. A resposta é um objeto Periodization com id, nome da periodização, número de semanas," +
                    "  data de início, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RecoveryPeriodizationDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryPeriodizationDTO getPeriodizationById(@PathVariable Long id){
        return periodizationService.getPeriodizationById(id);
    }

    @Operation(
            summary = "Atualiza periodização pelo Id",
            description = "Atualiza periodização pelo Id. A resposta é um objeto Periodization com id, nome da periodização, número de semanas," +
                    "  data de início, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RecoveryPeriodizationDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryPeriodizationDTO updatePeriodization(@PathVariable Long id, @RequestBody @Valid UpdatePeriodizationDTO updatePeriodizationDTO){
        return periodizationService.updatePeriodization(id, updatePeriodizationDTO);
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
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePeriodization(@PathVariable Long id){
        periodizationService.deletePeriodization(id);
    }

    @Operation(
            summary = "Retorna todas as periodizações",
            description = "Obtem todas as periodizações cadastradss na base de dados. A resposta é um array de objeto Periodization com id, nome da periodização, número de semanas," +
                    "  data de início, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RecoveryPeriodizationDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RecoveryPeriodizationDTO> getAllPeriodization(){
        return periodizationService.getAllPeriodization();
    }

    @Operation(
            summary = "Retorna todas as periodizações pelo Id do usuário",
            description = "Obtem todas as periodizações pelo Id do usuário. A resposta é um array de objeto Periodization com id, nome da periodização, número de semanas," +
                    "  data de início, " +
                    "data de criação e atualização."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RecoveryPeriodizationDTO.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ApiErrors.class), mediaType = "application/json") })
    })
    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<RecoveryPeriodizationDTO> getAllPeriodizationByIdUser(@PathVariable Long id){
        return periodizationService.getAllPeriodizationByUser(id);
    }
}
