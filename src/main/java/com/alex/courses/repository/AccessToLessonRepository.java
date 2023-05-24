package com.alex.courses.repository;

import com.alex.courses.entity.AccessToLesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessToLessonRepository extends JpaRepository<AccessToLesson, Long> {
    Optional<AccessToLesson> findByLessonId(Long lessonId);
}
