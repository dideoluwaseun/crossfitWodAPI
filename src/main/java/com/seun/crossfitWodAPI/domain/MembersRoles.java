package com.seun.crossfitWodAPI.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
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
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "members_id")
    private Members members;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "roles_id")
    private Roles roles;
    public String getRoleName() {
        return this.roles.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MembersRoles that = (MembersRoles) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
