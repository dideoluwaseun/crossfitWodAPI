package com.seun.crossfitwodapi.repository;

import com.seun.crossfitwodapi.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends JpaRepository <Workout, Long>{
    boolean existsByName(String name);

}
