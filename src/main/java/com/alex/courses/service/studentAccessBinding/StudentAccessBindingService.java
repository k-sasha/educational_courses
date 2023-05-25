package com.alex.courses.service.studentAccessBinding;

import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingRequestDto;
import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingResponseDto;
import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingUpdateDto;

import java.util.List;

public interface StudentAccessBindingService {
    List<StudentAccessBindingResponseDto> getAll();
    StudentAccessBindingResponseDto save(StudentAccessBindingRequestDto studentAccessBindingDto);
    StudentAccessBindingResponseDto update(Long id, StudentAccessBindingUpdateDto studentAccessBindingDto);
    StudentAccessBindingResponseDto get(Long id);
    void delete(Long id);

}
