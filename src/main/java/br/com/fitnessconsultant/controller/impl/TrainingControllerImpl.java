package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.TrainingController;
import br.com.fitnessconsultant.dto.training.RequestTrainingDTO;
import br.com.fitnessconsultant.dto.training.ResponseTrainingDTO;
import br.com.fitnessconsultant.service.training.TrainingService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/workouts")
public class TrainingControllerImpl implements TrainingController {

    private final TrainingService trainingService;

    public TrainingControllerImpl(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseTrainingDTO create(@RequestBody @NotNull RequestTrainingDTO requestTrainingDTO) {
        return trainingService.create(requestTrainingDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseTrainingDTO update(@PathVariable @Positive @NotNull Long id,
                                      @RequestBody @NotNull RequestTrainingDTO requestTrainingDTO) {
        return trainingService.update(id, requestTrainingDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable @Positive @NotNull Long id){
        trainingService.delete(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseTrainingDTO findById(@PathVariable @Positive @NotNull Long id){
        return trainingService.findById(id);
    }

    @GetMapping("/periodization/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<ResponseTrainingDTO> getAllTrainingByIdPeriodization(@PathVariable @Positive @NotNull Long id){
        return trainingService.getAllTrainingByIdPeriodization(id);
    }

}
