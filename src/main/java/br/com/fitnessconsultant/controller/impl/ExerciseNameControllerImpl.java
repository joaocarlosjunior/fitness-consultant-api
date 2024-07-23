package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.ExerciseNameController;
import br.com.fitnessconsultant.dto.exercisename.RequestExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.ResponseExerciseNameDTO;
import br.com.fitnessconsultant.service.exercisename.ExerciseNameService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercise-name")
public class ExerciseNameControllerImpl implements ExerciseNameController {

    private final ExerciseNameService exerciseNameService;

    public ExerciseNameControllerImpl(ExerciseNameService exerciseNameService) {
        this.exerciseNameService = exerciseNameService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @NotNull RequestExerciseNameDTO requestExerciseNameDTO) {
        exerciseNameService.create(requestExerciseNameDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseExerciseNameDTO findById(@PathVariable @Positive @NotNull Long id) {
        return exerciseNameService.findById(id);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseExerciseNameDTO update(@PathVariable @Positive @NotNull Long id,
                                          @RequestBody @NotNull RequestExerciseNameDTO requestExerciseNameDTO) {
        return exerciseNameService.update(id, requestExerciseNameDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @Positive @NotNull Long id) {
        exerciseNameService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseExerciseNameDTO> list() {
        return exerciseNameService.list();
    }

}
