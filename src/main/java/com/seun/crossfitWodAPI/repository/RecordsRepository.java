package com.seun.crossfitWodAPI.repository;

import com.seun.crossfitWodAPI.domain.Members;
import com.seun.crossfitWodAPI.domain.Records;
import com.seun.crossfitWodAPI.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordsRepository extends JpaRepository<Records, Long> {
    Records findByWod (Workout workoutId);
    Records findByMembers (Members membersId);

//select * from workout_records where workout id = specific id
}
