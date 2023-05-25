package com.alex.courses.repository;

import com.alex.courses.entity.StudentAccessBinding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentAccessBindingRepository extends JpaRepository<StudentAccessBinding, Long> {
    Optional<StudentAccessBinding> findByStudentIdAndCourseId(Long studentId, Long courseId);
}
