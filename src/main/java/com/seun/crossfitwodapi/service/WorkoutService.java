package com.seun.crossfitwodapi.service;

import com.seun.crossfitwodapi.domain.Workout;
import com.seun.crossfitwodapi.domain.dto.WorkoutDTO;

import java.util.List;


public interface WorkoutService {
    List<Workout> getAllWorkouts(Integer pageNo, Integer elementPerPage);
    Workout createNewWorkout(WorkoutDTO workoutDTO);
    Workout getOneWorkout(Long id);
    Workout updateOneWorkout(Long id, String mode, List<String> exercises, List<String> equipment, List<String> trainerTips);
    void deleteOneWorkout(Long id);
}
