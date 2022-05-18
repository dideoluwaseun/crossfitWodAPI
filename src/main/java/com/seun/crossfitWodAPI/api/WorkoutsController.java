package com.seun.crossfitWodAPI.api;

import com.seun.crossfitWodAPI.domain.Workouts;
import com.seun.crossfitWodAPI.domain.dto.WorkoutsDTO;
import com.seun.crossfitWodAPI.service.WorkoutService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/workouts", produces = {APPLICATION_JSON_VALUE})
@Slf4j
@Api(tags = "Workouts Resource")
public class WorkoutsController {
    private final WorkoutService workoutService;

    @GetMapping
    public ResponseEntity<List<Workouts>> getAllWorkouts(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "4") Integer elementPerPage) {
        return ResponseEntity.ok().body(workoutService.getAllWorkouts(pageNo, elementPerPage));
    }

    @PostMapping
    public ResponseEntity<Workouts> createNewWorkout(@Valid @RequestBody WorkoutsDTO workoutsDTO) {
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/workout")));
        return ResponseEntity.created(uri).body(workoutService.createNewWorkout(workoutsDTO));
    }

    @GetMapping (path = "{workoutId}")
    public ResponseEntity<Optional<Workouts>> getOneWorkout(@PathVariable Long workoutId) {
        return ResponseEntity.ok().body(workoutService.getOneWorkout(workoutId));
    }

    @PatchMapping(path = "{workoutId}")
    public ResponseEntity<Workouts> updateOneWorkout(@PathVariable Long workoutId,
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
