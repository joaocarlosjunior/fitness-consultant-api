package br.com.personalgymapi.controller;

import br.com.personalgymapi.dto.exercise.RecoveryExerciseDTO;
import br.com.personalgymapi.dto.exercise.RegisterExerciseDTO;
import br.com.personalgymapi.dto.exercise.UpdateIdTrainingExerciseDTO;
import br.com.personalgymapi.service.exercise.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecoveryExerciseDTO createExercise(@RequestBody @Valid RegisterExerciseDTO registerExerciseDTO){
        return exerciseService.createExercise(registerExerciseDTO);
    }

    @PatchMapping("/training/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addIdTrainingInExercise(@PathVariable Long id, @RequestBody UpdateIdTrainingExerciseDTO updateIdTrainingExerciseDTO){
        exerciseService.addIdTrainingInExercise(id, updateIdTrainingExerciseDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryExerciseDTO updateExercise(@PathVariable Long id, @RequestBody RegisterExerciseDTO registerExerciseDTO){
        return exerciseService.updateExercise(id, registerExerciseDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteExercise(@PathVariable Long id){
        exerciseService.deleteExercise(id);
    }

    @GetMapping("/training/{id}")
    public List<RecoveryExerciseDTO> getAllExercisesByIdTraining(@PathVariable Long id){
        return exerciseService.getAllExercisesByIdTraining(id);
    }
}
