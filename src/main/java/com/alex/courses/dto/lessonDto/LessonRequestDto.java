package com.alex.courses.dto.lessonDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonRequestDto {

    @NotBlank(message = "lesson name is required")
    @Size(min = 2, max = 100, message = "lesson name must be between 2 and 100 characters")
    private String lessonName;

    @NotNull(message = "course's id is required")
    @Digits(integer = 19, fraction = 0, message = "course's id must be a number")
    @Min(value = 1, message = "course's id must be greater than 0")
    @Max(value = Long.MAX_VALUE, message = "course's id must be less than or equal to " + Long.MAX_VALUE)
    private Long courseId;

}
