package com.alex.courses.repository;

import com.alex.courses.entity.TextTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TextTaskRepository extends JpaRepository<TextTask, Long> {
    Optional<TextTask> findByLessonIdAndTaskDescription(Long lessonId, String taskDescription);
}
