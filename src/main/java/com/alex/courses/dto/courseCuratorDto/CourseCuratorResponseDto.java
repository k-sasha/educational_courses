package com.alex.courses.dto.courseCuratorDto;

import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.curatorDto.CuratorResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseCuratorResponseDto {
    private Long id;
    private CourseResponseDto course;
    private CuratorResponseDto curator;
}
