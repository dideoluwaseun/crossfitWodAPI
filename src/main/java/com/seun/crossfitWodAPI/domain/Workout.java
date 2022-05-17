package com.seun.crossfitWodAPI.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seun.crossfitWodAPI.serviceImpl.ListToStringConverter;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
@AllArgsConstructor
@Table (name = "wod")
@TypeDefs({ @TypeDef( name = "list-array", typeClass = ListToStringConverter.class) })
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotEmpty
    @Column(unique = true)
    private String name;
    @NotNull
    private String mode;
    @Convert(converter = ListToStringConverter.class)
    private List<String> equipment;
    @Convert(converter = ListToStringConverter.class)
    private List<String> exercises;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @Convert(converter = ListToStringConverter.class)
    private List<String> trainerTips;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Workout workout = (Workout) o;
        return id != null && Objects.equals(id, workout.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}