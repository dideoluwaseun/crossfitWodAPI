package com.seun.crossfitWodAPI.service;

import com.seun.crossfitWodAPI.domain.Members;
import com.seun.crossfitWodAPI.domain.Records;
import com.seun.crossfitWodAPI.domain.Workout;
import com.seun.crossfitWodAPI.domain.dto.RecordsDTO;

import java.util.List;


public interface RecordsService {
    List<Records> getAllRecords(Integer pageNo, Integer elementPerPage);
    Records getRecordsByMembersId(Members memberId);
    Records getRecordsByWorkoutId(Workout workoutId);
    Records createNewRecord(RecordsDTO recordsDTO);
    void deleteOneRecord(Long recordId);
}
