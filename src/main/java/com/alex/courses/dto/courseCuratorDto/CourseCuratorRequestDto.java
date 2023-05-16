package com.alex.courses.dto.courseCuratorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Digits;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseCuratorRequestDto {
    @NotNull(message = "course's id is required")
    // ограничиваем количество цифр до 19 (максимальное количество цифр в Long), чтобы не использовать десятичные числа fraction = 0.
    @Digits(integer = 19, fraction = 0, message = "courseId must be a number")
    @Min(value = 1, message = "courseId must be greater than 0")
    @Max(value = Long.MAX_VALUE, message = "courseId must be less than or equal to " + Long.MAX_VALUE)
    private Long courseId;

    @NotNull(message = "curator's id is required")
    @Digits(integer = 19, fraction = 0, message = "curatorId must be a number")
    @Min(value = 1, message = "curatorId must be greater than 0")
    @Max(value = Long.MAX_VALUE, message = "curatorId must be less than or equal to " + Long.MAX_VALUE)
    private Long curatorId;
}
