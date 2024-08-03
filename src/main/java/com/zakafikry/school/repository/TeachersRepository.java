package com.zakafikry.school.repository;

import com.zakafikry.school.entity.Teachers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeachersRepository extends JpaRepository<Teachers, Long> {

    Optional<Teachers> findByUsername(String username);
}
