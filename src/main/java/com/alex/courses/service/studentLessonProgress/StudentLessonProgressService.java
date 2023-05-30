package com.alex.courses.service.studentLessonProgress;

import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressRequestDto;
import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressResponseDto;
import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressUpdateDto;

import java.util.List;

public interface StudentLessonProgressService {
    List<StudentLessonProgressResponseDto> getAll();
    StudentLessonProgressResponseDto save(StudentLessonProgressRequestDto studentLessonProgressDto);
    StudentLessonProgressResponseDto update(Long id, StudentLessonProgressUpdateDto studentLessonProgressDto);
    StudentLessonProgressResponseDto get(Long id);
    void delete(Long id);
}
