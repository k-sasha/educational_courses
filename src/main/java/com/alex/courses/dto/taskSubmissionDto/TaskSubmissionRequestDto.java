package com.alex.courses.dto.taskSubmissionDto;

import com.alex.courses.annotation_validation.annotation.ValidSubmissionType;
import com.alex.courses.entity.SubmissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskSubmissionRequestDto {
    @NotNull(message = "Text task's id is required")
    @Digits(integer = 19, fraction = 0, message = "Text task's id must be a number")
    @Min(value = 1, message = "Text task's id must be greater than 0")
    @Max(value = Long.MAX_VALUE, message = "Text task's id must be less than or equal to " + Long.MAX_VALUE)
    private Long textTaskId;

    @NotNull(message = "Student's id is required")
    @Digits(integer = 19, fraction = 0, message = "Student's id must be a number")
    @Min(value = 1, message = "Student's id must be greater than 0")
    @Max(value = Long.MAX_VALUE, message = "Student's id must be less than or equal to " + Long.MAX_VALUE)
    private Long studentId;

    @NotNull(message = "Curator's id is required")
    @Digits(integer = 19, fraction = 0, message = "Curator's id must be a number")
    @Min(value = 1, message = "Curator's id must be greater than 0")
    @Max(value = Long.MAX_VALUE, message = "Curator's id must be less than or equal to " + Long.MAX_VALUE)
    private Long curatorId;

    @NotBlank(message = "Submission text is required")
    @Size(min = 2, message = "Submission text must be min 2 symbols")
    private String submissionText;

    @NotNull(message = "Submission type is required")
    @ValidSubmissionType
    private SubmissionType submissionType;
}

