package com.seun.crossfitwodapi.service_implementation;

import com.seun.crossfitwodapi.domain.Workout;
import com.seun.crossfitwodapi.domain.dto.WorkoutDTO;
import com.seun.crossfitwodapi.exception.BadRequestException;
import com.seun.crossfitwodapi.exception.ResourceAlreadyExistsException;
import com.seun.crossfitwodapi.exception.ResourceNotFoundException;
import com.seun.crossfitwodapi.repository.WorkoutRepository;
import com.seun.crossfitwodapi.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
        boolean workoutName = workoutRepository.existsByName(workoutDTO.getName());
        if (workoutName) {
            throw new ResourceAlreadyExistsException("Workout already exists");
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
    public Workout getOneWorkout(Long id) {
        return workoutRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
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
        boolean workoutExists = workoutRepository.existsById(id);
        if (!workoutExists) {
            throw new ResourceNotFoundException();
        } else {
            workoutRepository.deleteById(id);
        }
    }
}
