package com.alex.courses.service.course;
import com.alex.courses.dto.courseDto.CourseRequestDto;
import com.alex.courses.dto.courseDto.CourseResponseDto;

import java.util.List;

public interface CourseService {
    List<CourseResponseDto> getAll();
    CourseResponseDto save(CourseRequestDto course);
    CourseResponseDto get(Long id);
    void delete(Long id);

}
