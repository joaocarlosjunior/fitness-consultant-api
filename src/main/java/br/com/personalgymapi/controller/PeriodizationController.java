package br.com.personalgymapi.controller;

import br.com.personalgymapi.dto.periodization.RecoveryPeriodizationDTO;
import br.com.personalgymapi.dto.periodization.RegisterPeriodizationDTO;
import br.com.personalgymapi.dto.periodization.UpdatePeriodizationDTO;
import br.com.personalgymapi.service.periodization.PeriodizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/periodization")
@RequiredArgsConstructor
public class PeriodizationController {
    private final PeriodizationService periodizationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecoveryPeriodizationDTO createPeriodization(@RequestBody @Valid RegisterPeriodizationDTO registerPeriodizationDTO) {
        return periodizationService.createPeriodization(registerPeriodizationDTO);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryPeriodizationDTO getPeriodizationById(@PathVariable Long id){
        return periodizationService.getPeriodizationById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryPeriodizationDTO updatePeriodization(@PathVariable Long id, @RequestBody @Valid UpdatePeriodizationDTO updatePeriodizationDTO){
        return periodizationService.updatePeriodization(id, updatePeriodizationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePeriodization(@PathVariable Long id){
        periodizationService.deletePeriodization(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RecoveryPeriodizationDTO> getAllPeriodization(){
        return periodizationService.getAllPeriodization();
    }
}
