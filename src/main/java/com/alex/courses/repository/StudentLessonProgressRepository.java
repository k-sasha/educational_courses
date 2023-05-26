package com.alex.courses.repository;

import com.alex.courses.entity.StudentLessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentLessonProgressRepository extends JpaRepository<StudentLessonProgress, Long> {
    Optional<StudentLessonProgress> findByStudentIdAndLessonId(Long studentId, Long lessonId);
    Long countByStudentIdAndCourseId(Long studentId, Long courseId);
}
