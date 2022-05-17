package com.seun.crossfitWodAPI.service;

import com.seun.crossfitWodAPI.domain.Workout;
import com.seun.crossfitWodAPI.domain.dto.WorkoutDTO;
import java.util.List;
import java.util.Optional;


public interface WorkoutService {
    List<Workout> getAllWorkouts(Integer pageNo, Integer elementPerPage);
    Workout createNewWorkout(WorkoutDTO workoutDTO);
    Optional<Workout> getOneWorkout(Long id);
    Workout updateOneWorkout(Long id, String mode, List<String> exercises, List<String> equipment, List<String> trainerTips);
    void deleteOneWorkout(Long id);
}
