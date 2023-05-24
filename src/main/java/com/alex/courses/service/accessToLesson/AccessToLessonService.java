package com.alex.courses.service.accessToLesson;

import com.alex.courses.dto.accessToLessonDto.AccessToLessonRequestDto;
import com.alex.courses.dto.accessToLessonDto.AccessToLessonResponseDto;

import java.util.List;

public interface AccessToLessonService {
    List<AccessToLessonResponseDto> getAll();
    AccessToLessonResponseDto save(AccessToLessonRequestDto accessToLessonDto);
    AccessToLessonResponseDto get(Long id);
    void delete(Long id);
}
