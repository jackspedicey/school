package com.zakafikry.school.service;

import com.zakafikry.school.dto.DataTablesInput;
import com.zakafikry.school.dto.DataTablesOutput;
import com.zakafikry.school.dto.EnrollmentDTO;
import com.zakafikry.school.entity.Courses;
import com.zakafikry.school.entity.Enrollment;
import com.zakafikry.school.entity.Students;
import com.zakafikry.school.repository.CoursesRepository;
import com.zakafikry.school.repository.EnrollmentRepository;
import com.zakafikry.school.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private DataTablesService dataTablesService;

    public void enrollStudent (Long courseId, Long studentId) {
        Optional<Students> optStudent = studentsRepository.findById(studentId);
        Optional<Courses> optCourse = coursesRepository.findById(courseId);
        if (optStudent.isPresent() && optCourse.isPresent()) {
            Students student = optStudent.get();
            Courses course = optCourse.get();
            Enrollment enrollment = new Enrollment();
            enrollment.setEnrolledDate(new Date());
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setName(course.getName() +" for " + student.getName());
            enrollmentRepository.save(enrollment);
        }
    }

    public DataTablesOutput<EnrollmentDTO> getEnrollmentDataTable(DataTablesInput input, Specification<Enrollment> spec) {
        DataTablesOutput<Enrollment> output = dataTablesService.getDataTableOutput(input, enrollmentRepository, enrollmentRepository, spec);

        List<EnrollmentDTO> dtos = output.getData().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        DataTablesOutput<EnrollmentDTO> dtoOutput = new DataTablesOutput<>();
        dtoOutput.setDraw(output.getDraw());
        dtoOutput.setRecordsTotal(output.getRecordsTotal());
        dtoOutput.setRecordsFiltered(output.getRecordsFiltered());
        dtoOutput.setData(dtos);

        return dtoOutput;
    }

    public EnrollmentDTO convertToDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setEnrollmentId(String.valueOf(enrollment.getId()));
        dto.setCourseName(enrollment.getCourse().getName());
        dto.setCourseDesc(enrollment.getCourse().getDescription());
        dto.setCourseLevel(enrollment.getCourse().getLevel());
        dto.setSchedule(enrollment.getCourse().getSchedule());
        dto.setEnrolledDate(String.valueOf(enrollment.getEnrolledDate()));
        dto.setStudentName(enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName());
        dto.setTeacherName(enrollment.getCourse().getTeacher().getFirstName() + " " + enrollment.getCourse().getTeacher().getLastName());
        dto.setCourseId(String.valueOf(enrollment.getCourse().getId()));
        return dto;
    }
}
