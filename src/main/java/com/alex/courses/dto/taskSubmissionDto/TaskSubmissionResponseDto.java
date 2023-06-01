package com.alex.courses.dto.taskSubmissionDto;

import com.alex.courses.entity.SubmissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskSubmissionResponseDto {
    private Long id;
    private Long textTaskId;
    private Long studentId;
    private Long curatorId;
    private String submissionText;
    private SubmissionType submissionType;
    private LocalDateTime timestamp;
}

