package com.seun.crossfitWodAPI.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "membersRoles")
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
//    private Collection<MembersRoles> membersRoles;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
