package com.alex.courses.dto.studentAccessBindingDto;

import com.alex.courses.dto.accessDto.AccessResponseDto;
import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.studentDto.StudentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentAccessBindingResponseDto {
    private Long id;
    private StudentResponseDto student;
    private CourseResponseDto course;
    private AccessResponseDto access;
}
