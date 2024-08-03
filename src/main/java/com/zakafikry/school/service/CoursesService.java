package com.zakafikry.school.service;

import com.zakafikry.school.dto.CourseDTO;
import com.zakafikry.school.dto.DataTablesInput;
import com.zakafikry.school.dto.DataTablesOutput;
import com.zakafikry.school.entity.Courses;
import com.zakafikry.school.entity.Teachers;
import com.zakafikry.school.repository.CoursesRepository;
import com.zakafikry.school.repository.TeachersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoursesService {

    @Autowired
    private CoursesRepository courseRepository;

    @Autowired
    private TeachersRepository teachersRepository;


    @Autowired
    private DataTablesService dataTablesService;

    public void registerCourse (CourseDTO c) {
        Optional<Teachers> optTeachers = teachersRepository.findByUsername(c.getUsername());
        if (optTeachers.isPresent()) {
            Teachers teachers = optTeachers.get();
            Courses courses = new Courses();
            courses.setCourseName(c.getCourseName());
            courses.setCourseDesc(c.getCourseDesc());
            courses.setCourseLevel(c.getCourseLevel());
            courses.setSchedule(c.getSchedule());
            courses.setTeacher(teachers);
            courseRepository.save(courses);
        }
    }

    public DataTablesOutput<CourseDTO> getCoursesForDataTable(DataTablesInput input, Specification<Courses> spec) {
        DataTablesOutput<Courses> output = dataTablesService.getDataTableOutput(input, courseRepository, courseRepository, spec);

        List<CourseDTO> dtos = output.getData().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        DataTablesOutput<CourseDTO> dtoOutput = new DataTablesOutput<>();
        dtoOutput.setDraw(output.getDraw());
        dtoOutput.setRecordsTotal(output.getRecordsTotal());
        dtoOutput.setRecordsFiltered(output.getRecordsFiltered());
        dtoOutput.setData(dtos);

        return dtoOutput;
    }

    private CourseDTO convertToDTO(Courses course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(String.valueOf(course.getId()));
        dto.setCourseName(course.getCourseName());
        dto.setCourseDesc(course.getCourseDesc());
        dto.setCourseLevel(course.getCourseLevel());
        dto.setSchedule(course.getSchedule());
        dto.setTeacherName(course.getTeacher() != null ? course.getTeacher().getFirstName() + course.getTeacher().getLastName() : null);
        return dto;
    }
}
