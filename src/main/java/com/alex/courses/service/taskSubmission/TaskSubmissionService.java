package com.alex.courses.service.taskSubmission;

import com.alex.courses.dto.taskSubmissionDto.TaskSubmissionRequestDto;
import com.alex.courses.dto.taskSubmissionDto.TaskSubmissionResponseDto;

import java.util.List;

public interface TaskSubmissionService {
    List<TaskSubmissionResponseDto> getAll();
    TaskSubmissionResponseDto save(TaskSubmissionRequestDto taskSubmissionDto);
    TaskSubmissionResponseDto get(Long id);
    void delete(Long id);
}
