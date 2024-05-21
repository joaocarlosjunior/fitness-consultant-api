package br.com.personalgymapi.controller.training;

import br.com.personalgymapi.dto.training.RecoveryTrainingDTO;
import br.com.personalgymapi.dto.training.RegisterTrainingDTO;
import br.com.personalgymapi.service.training.TrainingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workouts")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService){
        this.trainingService = trainingService;
    }

    @PostMapping
    public RecoveryTrainingDTO createTraining(@RequestBody @Valid RegisterTrainingDTO registerTrainingDTO){
        return trainingService.createTraining(registerTrainingDTO);
    }


}
