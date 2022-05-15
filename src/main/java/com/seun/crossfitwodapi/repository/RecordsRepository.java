package com.seun.crossfitwodapi.repository;

import com.seun.crossfitwodapi.domain.Members;
import com.seun.crossfitwodapi.domain.Records;
import com.seun.crossfitwodapi.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordsRepository extends JpaRepository<Records, Long> {
    Records findByWod (Workout workoutId);
    Records findByMembers (Members membersId);

//select * from workout_records where workout id = specific id
}
