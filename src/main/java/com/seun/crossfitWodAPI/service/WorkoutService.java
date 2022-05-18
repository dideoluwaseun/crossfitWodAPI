package com.seun.crossfitWodAPI.service;

import com.seun.crossfitWodAPI.domain.Workouts;
import com.seun.crossfitWodAPI.domain.dto.WorkoutsDTO;
import java.util.List;
import java.util.Optional;


public interface WorkoutService {
    List<Workouts> getAllWorkouts(Integer pageNo, Integer elementPerPage);
    Workouts createNewWorkout(WorkoutsDTO workoutsDTO);
    Optional<Workouts> getOneWorkout(Long id);
    Workouts updateOneWorkout(Long id, String mode, List<String> exercises, List<String> equipment, List<String> trainerTips);
    void deleteOneWorkout(Long id);
}
