package br.com.personalgymapi.controller;

import br.com.personalgymapi.dto.exercisename.RecoveryExerciseNameDTO;
import br.com.personalgymapi.dto.exercisename.RegisterExerciseNameDTO;
import br.com.personalgymapi.service.exercisename.ExerciseNameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exercise-name")
@RequiredArgsConstructor
public class ExerciseNameController {
    private final ExerciseNameService exerciseNameService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createExerciseName(@RequestBody @Valid RegisterExerciseNameDTO registerExerciseNameDTO){
        exerciseNameService.createExerciseName(registerExerciseNameDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryExerciseNameDTO getExerciseNameById(@PathVariable Long id){
        return exerciseNameService.getExerciseNameById(id);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public RecoveryExerciseNameDTO updateExerciseNameById(@PathVariable Long id,
                                              @RequestBody @Valid RegisterExerciseNameDTO registerExerciseNameDTO) {
        return exerciseNameService.updateExerciseNameById(id, registerExerciseNameDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrainingById(@PathVariable Long id){
        exerciseNameService.deleteExerciseNameById(id);
    }

}
