package com.zakafikry.school.repository;

import com.zakafikry.school.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentsRepository extends JpaRepository<Students, Long> {

    Optional<Students> findByUsername(String username);
}
