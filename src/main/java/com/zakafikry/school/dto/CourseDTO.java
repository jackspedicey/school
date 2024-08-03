package com.zakafikry.school.dto;

import lombok.Data;

@Data
public class CourseDTO {
    private String id;
    private String courseName;
    private String courseDesc;
    private String courseLevel;
    private String schedule;
    private String username;
    private String teacherName;
}