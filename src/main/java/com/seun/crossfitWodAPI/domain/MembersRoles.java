package com.seun.crossfitWodAPI.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "members_roles")
@NoArgsConstructor
public class MembersRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "members_id")
    private Members members;
    @ManyToOne
    @JoinColumn(name = "roles_id")
    private Roles roles;
}
