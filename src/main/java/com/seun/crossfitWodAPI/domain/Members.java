package com.seun.crossfitWodAPI.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
@Getter
@Setter
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@Table (name = "members")
public class Members  implements Serializable {
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
    @JsonManagedReference
    @OneToMany(mappedBy = "members", fetch = EAGER, cascade = CascadeType.ALL)
    private Set<MembersRoles> membersRoles = new HashSet<>();
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Members members = (Members) o;
        return id != null && Objects.equals(id, members.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
