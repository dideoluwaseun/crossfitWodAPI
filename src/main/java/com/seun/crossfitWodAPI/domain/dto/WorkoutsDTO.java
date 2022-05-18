package com.seun.crossfitWodAPI.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
@Data
@RequiredArgsConstructor
public class WorkoutsDTO {
    private String name;
    private String mode;
    private List<String> equipment;
    private List<String> exercises;
    private List<String> trainerTips;
}
