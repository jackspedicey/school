package com.zakafikry.school.service;

import com.zakafikry.school.dto.EnrollmentDTO;
import com.zakafikry.school.entity.Courses;
import com.zakafikry.school.entity.Enrollment;
import com.zakafikry.school.entity.Students;
import com.zakafikry.school.repository.CoursesRepository;
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

    @Autowired
    private CoursesRepository coursesRepository;

    public void enrollStudent (String courseId, String studentId) {
        Optional<Students> optStudent = studentsRepository.findById(Long.valueOf(studentId));
        Optional<Courses> optCourse = coursesRepository.findById(Long.valueOf(courseId));
        if (optStudent.isPresent() && optCourse.isPresent()) {
            Students student = optStudent.get();
            Courses course = optCourse.get();
            Enrollment enrollment = new Enrollment();
            enrollment.setEnrolledDate(new Date());
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollmentRepository.save(enrollment);
        }
    }

    public EnrollmentDTO convertToDTO(Enrollment enrollment) {
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setCourseName(enrollment.getCourse().getCourseName());
        enrollmentDTO.setCourseDesc(enrollment.getCourse().getCourseDesc());
        enrollmentDTO.setCourseLevel(enrollment.getCourse().getCourseLevel());
        enrollmentDTO.setEnrolledDate(String.valueOf(enrollment.getEnrolledDate()));
        enrollmentDTO.setStudentName(enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName());

        return enrollmentDTO;
    }
}
