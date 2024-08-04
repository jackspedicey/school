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
        return (root, query, builder) -> {
            if (search == null || search.isEmpty()) {
                return builder.conjunction();
            }
            String likePattern = "%" + search.toLowerCase() + "%";

            Join<Courses, Teachers> teacherJoin = root.join("teacher", JoinType.LEFT);

            return builder.or(
                    builder.like(builder.lower(root.get("name")), likePattern),
                    builder.like(builder.lower(root.get("description")), likePattern),
                    builder.like(builder.lower(root.get("level")), likePattern),
                    builder.like(builder.lower(root.get("schedule")), likePattern),
                    builder.like(builder.lower(teacherJoin.get("lastName")), likePattern),
                    builder.like(builder.lower(teacherJoin.get("firstName")), likePattern)
            );
        };
    }

    public static Specification<Enrollment> enrollmentWithSearch(String search) {
        return (root, query, builder) -> {
            if (search == null || search.isEmpty()) {
                return builder.conjunction();
            }
            String likePattern = "%" + search.toLowerCase() + "%";

            Join<Enrollment, Students> studentJoin = root.join("student", JoinType.LEFT);
            Join<Enrollment, Courses> courseJoin = root.join("course", JoinType.LEFT);
            Join<Courses, Teachers> teacherJoin = courseJoin.join("teacher", JoinType.LEFT);

            return builder.or(
                    builder.like(builder.lower(courseJoin.get("name")), likePattern),
                    builder.like(builder.lower(courseJoin.get("description")), likePattern),
                    builder.like(builder.lower(courseJoin.get("level")), likePattern),
                    builder.like(builder.lower(courseJoin.get("schedule")), likePattern),
                    builder.like(builder.lower(studentJoin.get("firstName")), likePattern),
                    builder.like(builder.lower(studentJoin.get("lastName")), likePattern),
                    builder.like(builder.lower(teacherJoin.get("firstName")), likePattern),
                    builder.like(builder.lower(teacherJoin.get("lastName")), likePattern),
                    builder.like(builder.function("DATE_FORMAT", String.class, root.get("enrolledDate"), builder.literal("%Y-%m-%d")), likePattern)
            );
        };
    }
}