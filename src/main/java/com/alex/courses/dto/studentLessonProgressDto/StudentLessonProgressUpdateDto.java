package com.alex.courses.dto.studentLessonProgressDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentLessonProgressUpdateDto {

    @NotNull(message = "curator's id is required")
    @Digits(integer = 19, fraction = 0, message = "curator's id must be a number")
    @Min(value = 1, message = "curator's id must be greater than 0")
    @Max(value = Long.MAX_VALUE, message = "curator's id must be less than or equal to " + Long.MAX_VALUE)
    private Long curatorId;

    @NotNull(message = "Lesson's done status is required")
    private Boolean done;

}

