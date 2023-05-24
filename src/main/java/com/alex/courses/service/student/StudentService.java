package com.alex.courses.service.student;

import com.alex.courses.dto.studentDto.StudentRequestDto;
import com.alex.courses.dto.studentDto.StudentResponseDto;
import com.alex.courses.dto.studentDto.StudentUpdateDto;

import java.util.List;

public interface StudentService {
    List<StudentResponseDto> getAll();
    StudentResponseDto save(StudentRequestDto studentDto);
    StudentResponseDto get(Long id);
    void delete(Long id);
    StudentUpdateDto update(Long id, StudentUpdateDto updatedStudentDto);
}
