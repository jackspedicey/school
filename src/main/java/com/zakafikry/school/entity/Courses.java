package com.zakafikry.school.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    private String courseDesc;
    private String courseLevel;
    //private Long teacherId;
    private String schedule;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teachers teacher;
}
