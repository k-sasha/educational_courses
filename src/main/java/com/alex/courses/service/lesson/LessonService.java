package com.alex.courses.service.lesson;

import com.alex.courses.dto.lessonDto.LessonRequestDto;
import com.alex.courses.dto.lessonDto.LessonResponseDto;

import java.util.List;

public interface LessonService {
    List<LessonResponseDto> getAll();
    LessonResponseDto save(LessonRequestDto lessonDto);
    LessonResponseDto get(Long id);
    void delete(Long id);
}
