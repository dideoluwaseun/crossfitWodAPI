package com.seun.crossfitWodAPI.repository;

import com.seun.crossfitWodAPI.domain.Records;
import com.seun.crossfitWodAPI.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends JpaRepository <Workout, Long>{
    boolean existsByName(String name);

}
