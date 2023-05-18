package com.alex.courses.service;

import com.alex.courses.dto.studentDto.StudentRequestDto;
import com.alex.courses.dto.studentDto.StudentResponseDto;
import com.alex.courses.dto.studentDto.StudentUpdateDto;
import com.alex.courses.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


@ExtendWith(MockitoExtension.class)
public class ModelMapperStudentTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Test
    public void shouldMapStudentRequestDtoToStudent() {
        // given
        StudentRequestDto studentRequestDto = new StudentRequestDto("Ivan", "Ivanov", "ivan@gmail.com");

        // when
        Student student = modelMapper.map(studentRequestDto, Student.class);

        // then
        Assertions.assertEquals(studentRequestDto.getName(), student.getName());
        Assertions.assertEquals(studentRequestDto.getSurname(), student.getSurname());
        Assertions.assertEquals(studentRequestDto.getEmail(), student.getEmail());
    }

    @Test
    public void shouldMapStudentToStudentResponseDto() {
        // given
        Student student = new Student(1L, "Ivan", "Ivanov", "ivan@gmail.com");

        // when
        StudentResponseDto studentResponseDto = modelMapper.map(student, StudentResponseDto.class);

        // then
        Assertions.assertEquals(student.getId(), studentResponseDto.getId());
        Assertions.assertEquals(student.getName(), studentResponseDto.getName());
        Assertions.assertEquals(student.getSurname(), studentResponseDto.getSurname());
    }

    @Test
    public void shouldMapStudentToStudentUpdateDto() {
        // given
        Student student = new Student(1L, "Ivan", "Ivanov", "ivan@gmail.com");

        // when
        StudentUpdateDto studentUpdateDto = modelMapper.map(student, StudentUpdateDto.class);

        // then
        Assertions.assertEquals(student.getEmail(), studentUpdateDto.getEmail());
    }

}