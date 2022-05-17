package com.seun.crossfitWodAPI.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RecordsDTO {
    private String record;
    private Long workoutId;
    private Long memberId;
}
