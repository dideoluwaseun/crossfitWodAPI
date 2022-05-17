package com.seun.crossfitWodAPI.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"workoutId", "memberId"})
@Table(name = "workout_records")
public class Records {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "workout_records")
    private String record;
    @JsonManagedReference
    @ManyToOne(targetEntity = Workout.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "wod_id", insertable = false, updatable = false)
    private Workout wod;
    @JsonManagedReference
    @ManyToOne(targetEntity = Members.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "members_id", insertable = false, updatable = false)
    private Members members;
    private Timestamp createdAt;
    @Column(name = "wod_id")
    private Long workoutId;
    @Column(name = "members_id")
    private Long memberId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Records records = (Records) o;
        return id != null && Objects.equals(id, records.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
