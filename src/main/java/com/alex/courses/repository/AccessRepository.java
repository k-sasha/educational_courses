package com.alex.courses.repository;

import com.alex.courses.entity.Access;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRepository extends JpaRepository<Access, Long> {
}
