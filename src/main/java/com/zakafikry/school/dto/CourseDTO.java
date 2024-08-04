package com.zakafikry.school.dto;

import lombok.Data;

@Data
public class CourseDTO {
    private String id;
    private String name;
    private String description;
    private String level;
    private String schedule;
    private String username;
    private String teacherName;
}