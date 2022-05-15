package com.seun.crossfitwodapi.domain;

import com.seun.crossfitwodapi.service_implementation.ListToStringConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Builder
@RequiredArgsConstructor
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
}