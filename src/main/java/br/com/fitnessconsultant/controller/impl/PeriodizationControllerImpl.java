package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.PeriodizationController;
import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;
import br.com.fitnessconsultant.service.periodization.PeriodizationService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/periodizations")
public class PeriodizationControllerImpl implements PeriodizationController {

    private final PeriodizationService periodizationService;

    public PeriodizationControllerImpl(PeriodizationService periodizationService){
        this.periodizationService = periodizationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponsePeriodizationDTO create(@RequestBody @NotNull RequestPeriodizationDTO requestPeriodizationDTO) {
        return periodizationService.create(requestPeriodizationDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponsePeriodizationDTO findById(@PathVariable @Positive @NotNull Long id){
        return periodizationService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponsePeriodizationDTO update(@PathVariable @Positive @NotNull Long id,
                                           @RequestBody @NotNull UpdatePeriodizationDTO updatePeriodizationDTO){
        return periodizationService.update(id, updatePeriodizationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable @Positive @NotNull Long id){
        periodizationService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<ResponsePeriodizationDTO> list(){
        return periodizationService.list();
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<ResponsePeriodizationDTO> getAllPeriodizationByIdUser(@PathVariable @Positive @NotNull Long id){
        return periodizationService.getAllPeriodizationByUser(id);
    }
}
