package com.seun.crossfitwodapi.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MembersDTO {
    private Long id;
    private String name;
    private String gender;
    private LocalDate dob;
    private String email;
    private String username;
    private String password;
}
