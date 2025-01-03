package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.PeriodizationController;
import br.com.fitnessconsultant.dto.periodization.RequestPeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.ResponsePeriodizationDTO;
import br.com.fitnessconsultant.dto.periodization.UpdatePeriodizationDTO;
import br.com.fitnessconsultant.service.periodization.PeriodizationService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
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

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponsePeriodizationDTO> create(@RequestBody @NotNull RequestPeriodizationDTO requestPeriodizationDTO) {
        return periodizationService.create(requestPeriodizationDTO);
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<ResponsePeriodizationDTO> findById(@PathVariable @Positive @NotNull Long id){
        return periodizationService.findById(id);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponsePeriodizationDTO> update(@PathVariable @Positive @NotNull Long id,
                                           @RequestBody @NotNull UpdatePeriodizationDTO updatePeriodizationDTO){
        return periodizationService.update(id, updatePeriodizationDTO);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id){
        periodizationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResponsePeriodizationDTO>> list(){
        return periodizationService.list();
    }

    @Override
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResponsePeriodizationDTO>> getAllPeriodizationByIdUser(@PathVariable @Positive @NotNull Long id){
        return periodizationService.getAllPeriodizationByUser(id);
    }
}
