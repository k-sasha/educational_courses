package com.alex.courses.dto.courseCuratorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseCuratorRequestDto {
    @NotBlank(message = "course's id is required")
    @Pattern(regexp = "\\d+", message = "courseId must be a number")
    private String courseId;

    @NotBlank(message = "curator's id is required")
    @Pattern(regexp = "\\d+", message = "curatorId must be a number")
    private String curatorId;
}
