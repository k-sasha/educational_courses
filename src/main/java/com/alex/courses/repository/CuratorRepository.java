package com.alex.courses.repository;

import com.alex.courses.entity.Curator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuratorRepository extends JpaRepository<Curator, Long> {
}
