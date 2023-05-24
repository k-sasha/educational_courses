package com.alex.courses.repository;

import com.alex.courses.entity.CourseCurator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseCuratorRepository extends JpaRepository<CourseCurator, Long> {
}
