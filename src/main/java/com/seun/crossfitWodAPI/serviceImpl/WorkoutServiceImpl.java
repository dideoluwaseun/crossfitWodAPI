package com.seun.crossfitWodAPI.serviceImpl;

import com.seun.crossfitWodAPI.domain.Workout;
import com.seun.crossfitWodAPI.domain.dto.WorkoutDTO;
import com.seun.crossfitWodAPI.exception.BadRequestException;
import com.seun.crossfitWodAPI.exception.ResourceAlreadyExistsException;
import com.seun.crossfitWodAPI.exception.ResourceNotFoundException;
import com.seun.crossfitWodAPI.repository.WorkoutRepository;
import com.seun.crossfitWodAPI.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {
    private final WorkoutRepository workoutRepository;

    @Override
    public List<Workout> getAllWorkouts(Integer pageNo, Integer elementPerPage) {
        Pageable workoutPage = PageRequest.of(pageNo, elementPerPage);
        return workoutRepository.findAll(workoutPage).getContent();
    }

    @Override
    @Transactional
    public Workout createNewWorkout(WorkoutDTO workoutDTO) {
        if(Objects.isNull(workoutDTO)) {
            throw new BadRequestException("No request body found");
        }
        if (workoutRepository.findByName(workoutDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Workout you are trying to save already exists");
        }
        if(workoutDTO.getName() == null || workoutDTO.getMode() == null  || workoutDTO.getEquipment() == null  || workoutDTO.getExercises() == null) {
            throw new BadRequestException("Either one of name, mode, equipment or exercises is missing");
        }
        return workoutRepository.save(Workout.builder()
                .name(workoutDTO.getName())
                .mode(workoutDTO.getMode())
                .equipment(workoutDTO.getEquipment())
                .exercises(workoutDTO.getExercises())
                .createdAt(new Timestamp(new Date().getTime()))
                .updatedAt(new Timestamp(new Date().getTime()))
                .trainerTips(workoutDTO.getTrainerTips())
                .build());
    }

    @Override
    public Optional<Workout> getOneWorkout(Long id) {
        if (workoutRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Workout does not exist");
        }
        log.info("Fetching details of workout with id {}", id );
        return workoutRepository.findById(id);
    }

    @Override
    public Workout updateOneWorkout(Long id, String mode, List<String> exercises, List<String> equipment, List<String> trainerTips) {
        Workout workout = workoutRepository.findById(id).orElseThrow(IllegalStateException::new);
        if (Objects.equals(workout.getMode(), mode) || Objects.equals(workout.getEquipment(), equipment) || Objects.equals(workout.getTrainerTips(), trainerTips)) {
            throw new ResourceAlreadyExistsException();
        }
        if ((mode!= null) && !Objects.equals(workout.getMode(), mode)) {
            workout.setMode(mode);
            workout.setUpdatedAt(new Timestamp(new Date().getTime()));
        }
        if ((equipment!= null) && !Objects.equals(workout.getEquipment(), equipment)) {
            workout.setEquipment(equipment);
            workout.setUpdatedAt(new Timestamp(new Date().getTime()));
        }
        if ((exercises!= null) && !Objects.equals(workout.getExercises(), exercises)) {
            workout.setExercises(exercises);
            workout.setUpdatedAt(new Timestamp(new Date().getTime()));
        }
        if ((trainerTips!= null) && !Objects.equals(workout.getTrainerTips(), trainerTips)) {
            workout.setTrainerTips(trainerTips);
            workout.setUpdatedAt(new Timestamp(new Date().getTime()));
        }
        return workoutRepository.save(workout);
    }

    @Override
    public void deleteOneWorkout(Long id) {
        if (!workoutRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        } else {
            log.info("Deleting details of workout with id {}", id );
            workoutRepository.deleteById(id);
        }
    }
}
