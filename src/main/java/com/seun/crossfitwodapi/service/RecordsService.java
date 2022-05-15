package com.seun.crossfitwodapi.service;

import com.seun.crossfitwodapi.domain.Members;
import com.seun.crossfitwodapi.domain.Records;
import com.seun.crossfitwodapi.domain.Workout;
import com.seun.crossfitwodapi.domain.dto.RecordsDTO;

import java.util.List;


public interface RecordsService {
    List<Records> getAllRecords(Integer pageNo, Integer elementPerPage);
    Records getRecordsByMembersId(Members memberId);
    Records getRecordsByWorkoutId(Workout workoutId);
    Records createNewRecord(RecordsDTO recordsDTO);
    void deleteOneRecord(Long recordId);
}
