package com.zakafikry.school.service;

import com.zakafikry.school.entity.Courses;
import com.zakafikry.school.entity.Teachers;
import com.zakafikry.school.repository.CoursesRepository;
import com.zakafikry.school.repository.TeachersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoursesService {

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private TeachersRepository teachersRepository;

    public void registerCourse (String courseName, String teacherId) {
        Optional<Teachers> optTeachers = teachersRepository.findById(Long.valueOf(teacherId));
        if (optTeachers.isPresent()) {
            Teachers teachers = optTeachers.get();
            Courses courses = new Courses();
            courses.setCourseName(courseName);
            courses.setCourseDesc(courseName + "by " + teachers.getFirstName() + " " + teachers.getLastName());
            courses.setTeacherId(teachers.getId());
            coursesRepository.save(courses);
        }

    }
}
