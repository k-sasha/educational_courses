package com.alex.courses.repository;

import com.alex.courses.entity.TaskSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskSubmissionRepository extends JpaRepository<TaskSubmission, Long> {

}
