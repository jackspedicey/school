package com.zakafikry.school.datatable;

import com.zakafikry.school.entity.Courses;
import com.zakafikry.school.entity.Enrollment;
import com.zakafikry.school.entity.Students;
import com.zakafikry.school.entity.Teachers;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class Specifications {
    public static Specification<Courses> courseWithSearch(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isEmpty()) {
                return cb.conjunction();
            }
            String likePattern = "%" + search.toLowerCase() + "%";

            // Join with Teacher entity
            Join<Courses, Teachers> teacherJoin = root.join("teacher", JoinType.LEFT);

            return cb.or(
                    cb.like(cb.lower(root.get("courseName")), likePattern),
                    cb.like(cb.lower(root.get("courseDesc")), likePattern),
                    cb.like(cb.lower(root.get("courseLevel")), likePattern),
                    cb.like(cb.lower(root.get("schedule")), likePattern),
                    cb.like(cb.lower(teacherJoin.get("name")), likePattern) // Search in teacher's name
            );
        };
    }

    public static Specification<Enrollment> enrollmentWithSearch(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isEmpty()) {
                return cb.conjunction();
            }
            String likePattern = "%" + search.toLowerCase() + "%";

            Join<Enrollment, Students> studentJoin = root.join("student", JoinType.LEFT);
            Join<Enrollment, Courses> courseJoin = root.join("course", JoinType.LEFT);
            Join<Courses, Teachers> teacherJoin = courseJoin.join("teacher", JoinType.LEFT);

            return cb.or(
                    cb.like(cb.lower(studentJoin.get("name")), likePattern),
                    cb.like(cb.lower(studentJoin.get("email")), likePattern),
                    cb.like(cb.lower(courseJoin.get("courseName")), likePattern),
                    cb.like(cb.lower(teacherJoin.get("name")), likePattern),
                    cb.like(cb.function("DATE_FORMAT", String.class, root.get("enrolledDate"), cb.literal("%Y-%m-%d")), likePattern)
            );
        };
    }
}