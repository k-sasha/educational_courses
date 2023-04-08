package com.alex.courses.repository;

import com.alex.courses.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Administrator, Integer> {
}
