package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.ExerciseController;
import br.com.fitnessconsultant.dto.exercise.RequestExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.ResponseExerciseDTO;
import br.com.fitnessconsultant.service.exercise.ExerciseService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseControllerImpl implements ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseControllerImpl(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseExerciseDTO create(@RequestBody @NotNull RequestExerciseDTO requestExerciseDTO){
        return exerciseService.create(requestExerciseDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseExerciseDTO update(@PathVariable @Positive @NotNull Long id, @RequestBody @NotNull RequestExerciseDTO requestExerciseDTO){
        return exerciseService.update(id, requestExerciseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive @NotNull Long id){
        exerciseService.delete(id);
    }

    @GetMapping("/training/{id}")
    public List<ResponseExerciseDTO> getAllExercisesByIdTraining(@PathVariable @Positive @NotNull Long id){
        return exerciseService.getAllExercisesByIdTraining(id);
    }
}
