package com.seun.crossfitwodapi.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "members_roles")
@NoArgsConstructor
public class MembersRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "members_id")
    private Members members;
    @ManyToOne
    @JoinColumn(name = "roles_id")
    private Roles roles;
}
