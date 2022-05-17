package com.seun.crossfitWodAPI.service;

import com.seun.crossfitWodAPI.domain.Records;
import com.seun.crossfitWodAPI.domain.dto.RecordsDTO;

import java.util.List;


public interface RecordsService {
    List<Records> getAllRecords(Integer pageNo, Integer elementPerPage);
    List<Records> getRecordsByMembersId(Long memberId, Integer pageNo, Integer elementPerPage);
    List<Records> getRecordsByWorkoutId(Long workoutId, Integer pageNo, Integer elementPerPage);
    Records createNewRecord(RecordsDTO recordsDTO);
    void deleteOneRecord(Long recordId);
}
