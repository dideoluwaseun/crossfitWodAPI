package com.seun.crossfitWodAPI.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table (name = "members")
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String gender;
    private LocalDate dob;
    private String email;
    private String username;
    private String password;
    @OneToMany(mappedBy = "members", fetch = EAGER)
    private Set<MembersRoles> membersRoles;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
