package com.alex.courses.service.student;

import com.alex.courses.dto.studentDto.StudentRequestDto;
import com.alex.courses.dto.studentDto.StudentResponseDto;
import com.alex.courses.dto.studentDto.StudentUpdateDto;

import java.util.List;

public interface StudentService {
    List<StudentResponseDto> getAllStudents();
    StudentResponseDto saveStudent(StudentRequestDto studentDto);
    StudentResponseDto getStudent(Long id);
    void deleteStudent(Long id);
    StudentUpdateDto updateStudent(Long id, StudentUpdateDto updatedStudentDto);
}
