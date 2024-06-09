package br.com.fitnessconsultant.mappers;

import br.com.fitnessconsultant.domain.entities.ExerciseName;
import br.com.fitnessconsultant.domain.entities.MuscleGroup;
import br.com.fitnessconsultant.dto.exercisename.ResponseExerciseNameDTO;
import br.com.fitnessconsultant.dto.exercisename.RequestExerciseNameDTO;
import org.springframework.stereotype.Component;

@Component
public class ExerciseNameMapper {

    public ResponseExerciseNameDTO toDto(ExerciseName exerciseName){
        if(exerciseName == null){
            return null;
        }
        return ResponseExerciseNameDTO
                .builder()
                .idExerciseName(exerciseName.getId())
                .exerciseName(exerciseName.getExerciseName())
                .muscleGroup(exerciseName.getMuscleGroup().getName())
                .build();
    }

    public ExerciseName toEntity(RequestExerciseNameDTO dto, MuscleGroup muscleGroup){
        if(dto == null){
            return null;
        }
        return ExerciseName
                .builder()
                .exerciseName(dto.getExerciseName())
                .muscleGroup(muscleGroup)
                .build();
    }

}
