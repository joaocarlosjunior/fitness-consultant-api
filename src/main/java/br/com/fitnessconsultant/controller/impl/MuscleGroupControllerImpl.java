package br.com.fitnessconsultant.controller.impl;

import br.com.fitnessconsultant.controller.MuscleGroupController;
import br.com.fitnessconsultant.dto.musuculegroup.RequestMuscleGroupDTO;
import br.com.fitnessconsultant.dto.musuculegroup.ResponseMuscleGroupDTO;
import br.com.fitnessconsultant.service.musclegroup.MuscleGroupService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/muscle-group")
public class MuscleGroupControllerImpl implements MuscleGroupController {
    private final MuscleGroupService muscleGroupService;

    public MuscleGroupControllerImpl(MuscleGroupService muscleGroupService) {
        this.muscleGroupService = muscleGroupService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @NotNull RequestMuscleGroupDTO requestMuscleGroupDTO) {
        muscleGroupService.create(requestMuscleGroupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive @NotNull Long id) {
        muscleGroupService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMuscleGroupDTO> update(@PathVariable @Positive @NotNull Long id,
                                         @RequestBody @NotNull RequestMuscleGroupDTO requestMuscleGroupDTO) {
        return muscleGroupService.update(id, requestMuscleGroupDTO);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMuscleGroupDTO> findById(@PathVariable @Positive @NotNull Long id) {
        return muscleGroupService.findById(id);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ResponseMuscleGroupDTO>> list() {
        return muscleGroupService.list();
    }

}
