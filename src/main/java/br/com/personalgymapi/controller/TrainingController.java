package br.com.personalgymapi.controller;

import br.com.personalgymapi.dto.training.RecoveryTrainingDTO;
import br.com.personalgymapi.dto.training.RegisterTrainingDTO;
import br.com.personalgymapi.service.training.TrainingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/workouts")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecoveryTrainingDTO createTraining(@RequestBody @Valid RegisterTrainingDTO registerTrainingDTO) {
        return trainingService.createTraining(registerTrainingDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public RecoveryTrainingDTO updateTraining(@PathVariable Long id,
                                              @RequestBody @Valid RegisterTrainingDTO registerTrainingDTO) {
        return trainingService.updateTraining(id, registerTrainingDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTraining(@PathVariable Long id){
        trainingService.deleteTraining(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryTrainingDTO getTrainingById(@PathVariable Long id){
        return trainingService.getTrainingById(id);
    }

    @GetMapping("/periodization/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<RecoveryTrainingDTO> getAllTrainingByIdPeriodization(@PathVariable Long id){
        return trainingService.getAllTrainingByIdPeriodization(id);
    }

}
