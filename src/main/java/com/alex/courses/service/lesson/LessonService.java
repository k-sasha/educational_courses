package com.alex.courses.service.lesson;

import com.alex.courses.dto.lessonDto.LessonRequestDto;
import com.alex.courses.dto.lessonDto.LessonResponseDto;

import java.util.List;

public interface LessonService {
    List<LessonResponseDto> getAllLessons();
    LessonResponseDto saveLesson(LessonRequestDto lessonDto);
    LessonResponseDto getLesson(Long id);
    void deleteLesson(Long id);
}
