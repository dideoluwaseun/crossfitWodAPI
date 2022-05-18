package com.seun.crossfitWodAPI.serviceImpl;

import com.seun.crossfitWodAPI.domain.Workouts;
import com.seun.crossfitWodAPI.domain.dto.WorkoutsDTO;
import com.seun.crossfitWodAPI.exception.BadRequestException;
import com.seun.crossfitWodAPI.exception.ResourceAlreadyExistsException;
import com.seun.crossfitWodAPI.exception.ResourceNotFoundException;
import com.seun.crossfitWodAPI.repository.WorkoutRepository;
import com.seun.crossfitWodAPI.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value = "Workouts")
    @Override
    public List<Workouts> getAllWorkouts(Integer pageNo, Integer elementPerPage) {
        Pageable workoutPage = PageRequest.of(pageNo, elementPerPage);
        return workoutRepository.findAll(workoutPage).getContent();
    }

    @Override
    @Transactional
    public Workouts createNewWorkout(WorkoutsDTO workoutsDTO) {
        if(Objects.isNull(workoutsDTO)) {
            throw new BadRequestException("No request body found");
        }
        if (workoutRepository.findByName(workoutsDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Workout you are trying to save already exists");
        }
        if(workoutsDTO.getName() == null || workoutsDTO.getMode() == null  || workoutsDTO.getEquipment() == null  || workoutsDTO.getExercises() == null) {
            throw new BadRequestException("Either one of name, mode, equipment or exercises is missing");
        }
        return workoutRepository.save(Workouts.builder()
                .name(workoutsDTO.getName())
                .mode(workoutsDTO.getMode())
                .equipment(workoutsDTO.getEquipment())
                .exercises(workoutsDTO.getExercises())
                .createdAt(new Timestamp(new Date().getTime()))
                .updatedAt(new Timestamp(new Date().getTime()))
                .trainerTips(workoutsDTO.getTrainerTips())
                .build());
    }

    @Cacheable(value = "Workouts", key = "#id")
    @Override
    public Optional<Workouts> getOneWorkout(Long id) {
        if (workoutRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Workout does not exist");
        }
        log.info("Fetching details of workout with id {}", id );
        return workoutRepository.findById(id);
    }

    @CachePut(value = "Workouts", key = "#id")
    @Override
    public Workouts updateOneWorkout(Long id, String mode, List<String> exercises, List<String> equipment, List<String> trainerTips) {
        Workouts workouts = workoutRepository.findById(id).orElseThrow(IllegalStateException::new);
        if (Objects.equals(workouts.getMode(), mode) || Objects.equals(workouts.getEquipment(), equipment) || Objects.equals(workouts.getTrainerTips(), trainerTips)) {
            throw new ResourceAlreadyExistsException();
        }
        if ((mode!= null) && !Objects.equals(workouts.getMode(), mode)) {
            workouts.setMode(mode);
            workouts.setUpdatedAt(new Timestamp(new Date().getTime()));
        }
        if ((equipment!= null) && !Objects.equals(workouts.getEquipment(), equipment)) {
            workouts.setEquipment(equipment);
            workouts.setUpdatedAt(new Timestamp(new Date().getTime()));
        }
        if ((exercises!= null) && !Objects.equals(workouts.getExercises(), exercises)) {
            workouts.setExercises(exercises);
            workouts.setUpdatedAt(new Timestamp(new Date().getTime()));
        }
        if ((trainerTips!= null) && !Objects.equals(workouts.getTrainerTips(), trainerTips)) {
            workouts.setTrainerTips(trainerTips);
            workouts.setUpdatedAt(new Timestamp(new Date().getTime()));
        }
        return workoutRepository.save(workouts);
    }

    @CacheEvict(value = "Workouts", key = "#id")
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
