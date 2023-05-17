package com.alex.courses.service.course;
import com.alex.courses.dto.courseDto.CourseRequestDto;
import com.alex.courses.dto.courseDto.CourseResponseDto;

import java.util.List;

public interface CourseService {
    List<CourseResponseDto> getAllCourses();
    CourseResponseDto saveCourse(CourseRequestDto course);
    CourseResponseDto getCourse(Long id);
    void deleteCourse(Long id);

}
