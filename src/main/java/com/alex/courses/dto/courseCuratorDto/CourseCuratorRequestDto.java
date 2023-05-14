package com.alex.courses.dto.courseCuratorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseCuratorRequestDto {
    @NotBlank(message = "courseId is required")
    private Long courseId;

    @NotBlank(message = "curatorId is required")
    private Long curatorId;
}
