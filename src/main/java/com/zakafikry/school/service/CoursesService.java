package com.zakafikry.school.service;

import com.zakafikry.school.dto.CourseDTO;
import com.zakafikry.school.dto.DataTablesInput;
import com.zakafikry.school.dto.DataTablesOutput;
import com.zakafikry.school.entity.Courses;
import com.zakafikry.school.entity.Teachers;
import com.zakafikry.school.repository.CoursesRepository;
import com.zakafikry.school.repository.TeachersRepository;
import com.zakafikry.school.repository.UsersRepository;
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
    UsersRepository usersRepository;

    @Autowired
    private DataTablesService dataTablesService;

    public void registerCourse (CourseDTO c) {
        Optional<Teachers> optTeachers = teachersRepository.findByUsername(c.getUsername());
        if (optTeachers.isPresent()) {
            Teachers teachers = optTeachers.get();
            Courses courses = new Courses();
            courses.setName(c.getName());
            courses.setDescription(c.getDescription());
            courses.setLevel(c.getLevel());
            courses.setSchedule(c.getSchedule());
            courses.setTeacher(teachers);
            courseRepository.save(courses);
        }
    }

    public DataTablesOutput<CourseDTO> getCoursesDataTable(DataTablesInput input, Specification<Courses> spec) {
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

    public CourseDTO convertToDTO(Courses course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(String.valueOf(course.getId()));
        dto.setName(course.getName());
        dto.setDescription(course.getDescription());
        dto.setLevel(course.getLevel());
        dto.setSchedule(course.getSchedule());
        dto.setTeacherName(course.getTeacher() != null ? course.getTeacher().getFirstName() + " " + course.getTeacher().getLastName() : null);
        return dto;
    }
}
