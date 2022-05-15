package com.seun.crossfitwodapi.domain.dto;

import com.seun.crossfitwodapi.domain.Members;
import com.seun.crossfitwodapi.domain.Workout;
import lombok.Data;

@Data
public class RecordsDTO {
    private String record;
    private Workout workoutId;
    private Members memberId;
}
