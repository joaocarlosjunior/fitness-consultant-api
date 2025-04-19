package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.TrainingController;
import br.com.fitnessconsultant.dto.training.RequestTrainingDTO;
import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;
import br.com.fitnessconsultant.service.training.TrainingService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/workouts")
public class TrainingControllerImpl implements TrainingController {

    private final TrainingService trainingService;

    public TrainingControllerImpl(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Override
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseTrainingDTO> create(
            @RequestBody @NotNull @Validated RequestTrainingDTO requestTrainingDTO
    ) {
        return trainingService.create(requestTrainingDTO);
    }

    @Override
    @PutMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseTrainingDTO> update(
            @PathVariable @Positive @NotNull Long id,
            @RequestBody @NotNull @Validated RequestTrainingDTO requestTrainingDTO
    ) {
        return trainingService.update(id, requestTrainingDTO);
    }

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id){
        trainingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ResponseTrainingDTO> findById(@PathVariable @Positive @NotNull Long id){
        return trainingService.findById(id);
    }

    @Override
    @GetMapping("/periodization/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResponseTrainingDTO>> getAllTrainingByIdPeriodization(@PathVariable @Positive @NotNull Long id){
        return trainingService.getAllTrainingByIdPeriodization(id);
    }

}
