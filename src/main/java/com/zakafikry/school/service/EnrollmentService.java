package com.zakafikry.school.service;

import com.zakafikry.school.entity.Enrollment;
import com.zakafikry.school.entity.Students;
import com.zakafikry.school.repository.EnrollmentRepository;
import com.zakafikry.school.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentsRepository studentsRepository;

    public void enrollStudent (String courseId, String studentId) {
        Optional<Students> optStudent = studentsRepository.findById(Long.valueOf(studentId));
        if (optStudent.isPresent()) {
            Students students = optStudent.get();
            Enrollment enrollment = new Enrollment();
            enrollment.setEnrolledDate(new Date());
            enrollment.setStudentId(Long.valueOf(studentId));
            enrollment.setCourseId(Long.valueOf(courseId));
            enrollmentRepository.save(enrollment);
        }
    }
}
