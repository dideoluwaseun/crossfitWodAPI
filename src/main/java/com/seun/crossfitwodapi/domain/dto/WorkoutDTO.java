package com.seun.crossfitwodapi.domain.dto;

import lombok.Data;

import java.util.List;
@Data
public class WorkoutDTO {
    private String name;
    private String mode;
    private List<String> equipment;
    private List<String> exercises;
    private List<String> trainerTips;
}
