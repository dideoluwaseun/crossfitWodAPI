package com.seun.crossfitWodAPI.repository;

import com.seun.crossfitWodAPI.domain.Records;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordsRepository extends JpaRepository<Records, Long> {
    List<Records> findByWorkoutId (Long workoutId);
    List<Records> findByMemberId(Long memberId);
}
