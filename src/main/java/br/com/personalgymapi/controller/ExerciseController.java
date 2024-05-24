package br.com.personalgymapi.controller;

import br.com.personalgymapi.dto.exercise.RecoveryExerciseDTO;
import br.com.personalgymapi.dto.exercise.RegisterExerciseDTO;
import br.com.personalgymapi.dto.exercise.UpdateIdTrainingExerciseDTO;
import br.com.personalgymapi.service.exercise.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exercise")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecoveryExerciseDTO createExercise(@RequestBody @Valid RegisterExerciseDTO registerExerciseDTO){
        return exerciseService.createExercise(registerExerciseDTO);
    }

    @PutMapping("/training/{id}")
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
}
