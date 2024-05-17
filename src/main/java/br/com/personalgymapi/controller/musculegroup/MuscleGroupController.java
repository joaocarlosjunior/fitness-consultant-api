package br.com.personalgymapi.controller.musculegroup;

import br.com.personalgymapi.dto.musuculegroup.RecoveryMuscleGroupDTO;
import br.com.personalgymapi.dto.musuculegroup.RegisterMuscleGroupDTO;
import br.com.personalgymapi.service.musclegroup.MuscleGroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/muscle-group")
public class MuscleGroupController {
    private final MuscleGroupService muscleGroupService;

    public MuscleGroupController(MuscleGroupService muscleGroupService){
        this.muscleGroupService = muscleGroupService;
    }

    //ADMIN
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMuscleGroup(@RequestBody @Valid RegisterMuscleGroupDTO registerMuscleGroupDTO){
        muscleGroupService.addMuscleGroup(registerMuscleGroupDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        muscleGroupService.deletedById(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryMuscleGroupDTO update(@PathVariable Long id, @RequestBody RegisterMuscleGroupDTO registerMuscleGroupDTO){
        return muscleGroupService.update(id, registerMuscleGroupDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RecoveryMuscleGroupDTO getMuscleGroupById(@PathVariable Long id){
        return muscleGroupService.getMuscleGroupById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RecoveryMuscleGroupDTO> getAllMuscleGroup(){
        return muscleGroupService.getAllMuscleGroups();
    }


}
