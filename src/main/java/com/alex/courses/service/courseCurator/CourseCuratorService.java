package com.alex.courses.service.courseCurator;

import com.alex.courses.dto.courseCuratorDto.CourseCuratorRequestDto;
import com.alex.courses.dto.courseCuratorDto.CourseCuratorResponseDto;

import java.util.List;

public interface CourseCuratorService {
    List<CourseCuratorResponseDto> getAllCoursesCurators();
    CourseCuratorResponseDto saveCourseCurator(CourseCuratorRequestDto courseCuratorDto);
    CourseCuratorResponseDto getCourseCurator(Long id);
    void deleteCourseCurator(Long id);
}
