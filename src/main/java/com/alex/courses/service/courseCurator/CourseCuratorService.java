package com.alex.courses.service.courseCurator;

import com.alex.courses.dto.courseCuratorDto.CourseCuratorRequestDto;
import com.alex.courses.dto.courseCuratorDto.CourseCuratorResponseDto;

import java.util.List;

public interface CourseCuratorService {
    List<CourseCuratorResponseDto> getAll();
    CourseCuratorResponseDto save(CourseCuratorRequestDto courseCuratorDto);
    CourseCuratorResponseDto get(Long id);
    void delete(Long id);
}
