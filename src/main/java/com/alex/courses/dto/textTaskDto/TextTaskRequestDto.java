package com.alex.courses.dto.textTaskDto;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TextTaskRequestDto {
    @NotBlank(message = "Task description is required")
    @Size(min = 2, message = "name must be min 2 symbols")
    private String taskDescription;

    @NotNull(message = "Lesson's id is required")
    @Digits(integer = 19, fraction = 0, message = "Lesson's id must be a number")
    @Min(value = 1, message = "Lesson's id must be greater than 0")
    @Max(value = Long.MAX_VALUE, message = "Lesson's id must be less than or equal to " + Long.MAX_VALUE)
    private Long lessonId;
}

