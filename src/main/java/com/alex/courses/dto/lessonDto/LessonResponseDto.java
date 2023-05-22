package com.alex.courses.dto.lessonDto;

import com.alex.courses.dto.courseDto.CourseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonResponseDto {
    private Long id;
    private String lessonName;
    private CourseResponseDto course;
}
