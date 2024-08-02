package com.zakafikry.school.repository;

import com.zakafikry.school.entity.Teachers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachersRepository extends JpaRepository<Teachers, Long> {
    Teachers findByUsername(String username);
}
