package com.seun.crossfitWodAPI.api;

import com.seun.crossfitWodAPI.domain.Workout;
import com.seun.crossfitWodAPI.domain.dto.WorkoutDTO;
import com.seun.crossfitWodAPI.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/workout")
@Slf4j
public class WorkoutController {
    private final WorkoutService workoutService;

    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts(@RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "4") Integer elementPerPage) {
        return ResponseEntity.ok().body(workoutService.getAllWorkouts(pageNo, elementPerPage));
    }


    @PostMapping()
    public ResponseEntity<Workout> createNewWorkout(@Valid @RequestBody WorkoutDTO workoutDTO) {
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/workout")));
        return ResponseEntity.created(uri).body(workoutService.createNewWorkout(workoutDTO));
    }

    @GetMapping (path = "{workoutId}")
    public ResponseEntity<Optional<Workout>> getOneWorkout(@PathVariable Long workoutId) {
        return ResponseEntity.ok().body(workoutService.getOneWorkout(workoutId));
    }

    @PatchMapping(path = "{workoutId}")
    public ResponseEntity<Workout> updateOneWorkout(@PathVariable Long workoutId,
                                          @RequestParam(required = false) String mode,
                                          @RequestParam(required = false) List<String> exercises,
                                          @RequestParam(required = false) List<String> equipment,
                                          @RequestParam(required = false) List<String> trainerTips) {
        return ResponseEntity.ok().body(workoutService.updateOneWorkout(workoutId, mode, exercises, equipment, trainerTips));
    }

    @DeleteMapping(path = "{workoutId}")
    public ResponseEntity<String> deleteOneWorkout(@PathVariable Long workoutId) {
        workoutService.deleteOneWorkout(workoutId);
        return ResponseEntity.noContent().build();
    }

}
