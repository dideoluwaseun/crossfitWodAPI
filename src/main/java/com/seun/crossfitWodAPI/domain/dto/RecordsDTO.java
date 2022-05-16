package com.seun.crossfitWodAPI.domain.dto;

import com.seun.crossfitWodAPI.domain.Members;
import com.seun.crossfitWodAPI.domain.Workout;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
//@Builder
@RequiredArgsConstructor
public class RecordsDTO {
    private String record;
    private Workout workoutId;
    private Members memberId;
}
