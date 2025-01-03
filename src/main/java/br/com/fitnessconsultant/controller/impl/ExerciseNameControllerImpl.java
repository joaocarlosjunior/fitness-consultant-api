package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.ExerciseNameController;
import br.com.fitnessconsultant.dto.exercisename.RequestExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.ResponseExerciseNameDTO;
import br.com.fitnessconsultant.service.exercisename.ExerciseNameService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercise-name")
public class ExerciseNameControllerImpl implements ExerciseNameController {

    private final ExerciseNameService exerciseNameService;

    public ExerciseNameControllerImpl(ExerciseNameService exerciseNameService) {
        this.exerciseNameService = exerciseNameService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @NotNull RequestExerciseNameDTO requestExerciseNameDTO) {
        exerciseNameService.create(requestExerciseNameDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseExerciseNameDTO> findById(@PathVariable @Positive @NotNull Long id) {
        return exerciseNameService.findById(id);
    }

    @Override
    @PutMapping({"/{id}"})
    public ResponseEntity<ResponseExerciseNameDTO> update(@PathVariable @Positive @NotNull Long id,
                                          @RequestBody @NotNull RequestExerciseNameDTO requestExerciseNameDTO) {
        return exerciseNameService.update(id, requestExerciseNameDTO);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id) {
        exerciseNameService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ResponseExerciseNameDTO>> list() {
        return exerciseNameService.list();
    }

}
