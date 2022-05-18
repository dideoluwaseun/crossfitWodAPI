package com.seun.crossfitWodAPI.repository;

import com.seun.crossfitWodAPI.domain.Workouts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository <Workouts, Long>{
    Optional<Workouts> findByName(String name);

}
