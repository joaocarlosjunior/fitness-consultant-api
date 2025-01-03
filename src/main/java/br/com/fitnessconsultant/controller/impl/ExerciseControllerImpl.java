package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.ExerciseController;
import br.com.fitnessconsultant.dto.exercise.RequestExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.ResponseExerciseDTO;
import br.com.fitnessconsultant.service.exercise.ExerciseService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
public class ExerciseControllerImpl implements ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseControllerImpl(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
    }

    @Override
    @PostMapping
    public ResponseEntity<ResponseExerciseDTO> create(@RequestBody @NotNull RequestExerciseDTO requestExerciseDTO){
        return exerciseService.create(requestExerciseDTO);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ResponseExerciseDTO> update(@PathVariable @Positive @NotNull Long id, @RequestBody @NotNull RequestExerciseDTO requestExerciseDTO){
        return exerciseService.update(id, requestExerciseDTO);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id){
        exerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/training/{id}")
    public ResponseEntity<List<ResponseExerciseDTO>> getAllExercisesByIdTraining(@PathVariable @Positive @NotNull Long id){
        return exerciseService.getAllExercisesByIdTraining(id);
    }
}
