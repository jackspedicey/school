package com.zakafikry.school.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String level;
    private String schedule;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teachers teacher;
}