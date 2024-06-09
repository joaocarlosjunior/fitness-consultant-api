package br.com.fitnessconsultant.mappers;

import br.com.fitnessconsultant.domain.entities.Exercise;
import br.com.fitnessconsultant.domain.entities.ExerciseName;
import br.com.fitnessconsultant.domain.entities.Training;
import br.com.fitnessconsultant.dto.exercise.ResponseExerciseDTO;
import br.com.fitnessconsultant.dto.exercise.RequestExerciseDTO;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {

    public ResponseExerciseDTO toDto(Exercise exercise){
        if(exercise == null){
            return null;
        }
        return ResponseExerciseDTO
                .builder()
                .idExercise(exercise.getId())
                .exerciseName(exercise.getExerciseName().getExerciseName())
                .finalLoad(exercise.getFinalLoad())
                .initialLoad(exercise.getInitialLoad())
                .method(exercise.getMethod())
                .repetitions(exercise.getRepetitions())
                .series(exercise.getSeries())
                .build();
    }

    public Exercise toEntity(RequestExerciseDTO dto, ExerciseName exerciseName, Training training){
        if(dto == null){
            return null;
        }
        return Exercise
                .builder()
                .exerciseName(exerciseName)
                .finalLoad(dto.getFinalLoad())
                .initialLoad(dto.getInitialLoad())
                .repetitions(dto.getRepetitions())
                .series(dto.getSeries())
                .training(training)
                .method(dto.getMethod())
                .build();
    }
}
