package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.MuscleGroupController;
import br.com.fitnessconsultant.dto.musuculegroup.RequestMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.ResponseMuscleGroupDTO;
import br.com.fitnessconsultant.service.musclegroup.MuscleGroupService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/muscle-group")
public class MuscleGroupControllerImpl implements MuscleGroupController {
    private final MuscleGroupService muscleGroupService;

    public MuscleGroupControllerImpl(MuscleGroupService muscleGroupService) {
        this.muscleGroupService = muscleGroupService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @NotNull RequestMuscleGroupDTO requestMuscleGroupDTO) {
        muscleGroupService.create(requestMuscleGroupDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive @NotNull Long id) {
        muscleGroupService.delete(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMuscleGroupDTO update(@PathVariable @Positive @NotNull Long id,
                                         @RequestBody @NotNull RequestMuscleGroupDTO requestMuscleGroupDTO) {
        return muscleGroupService.update(id, requestMuscleGroupDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMuscleGroupDTO findById(@PathVariable @Positive @NotNull Long id) {
        return muscleGroupService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseMuscleGroupDTO> list() {
        return muscleGroupService.list();
    }

}
