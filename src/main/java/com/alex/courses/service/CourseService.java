package com.alex.courses.service;
import com.alex.courses.dto.courseDto.CourseRequestDto;
import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.entity.Course;

import java.util.List;

public interface CourseService {
    List<CourseResponseDto> getAllCorses();
    CourseResponseDto saveCourse(CourseRequestDto course);
    CourseResponseDto getCourse(Long id);
    void deleteCourse(Long id);

}
