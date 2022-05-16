package com.seun.crossfitWodAPI.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class MembersDTO {
    private Long id;
    private String name;
    private String gender;
    private LocalDate dob;
    private String email;
    private String username;
    private String password;
}
