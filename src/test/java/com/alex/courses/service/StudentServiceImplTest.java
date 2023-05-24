package com.alex.courses.service;

import com.alex.courses.dto.studentDto.StudentRequestDto;
import com.alex.courses.dto.studentDto.StudentResponseDto;
import com.alex.courses.dto.studentDto.StudentUpdateDto;
import com.alex.courses.entity.Student;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.StudentRepository;
import com.alex.courses.service.student.StudentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) // for work annotations @Mock and @InjectMocks
public class StudentServiceImplTest {

    @InjectMocks //i.e. upload this mocks (studentRepository and modelMapper)
    // to the service (studentService)
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMapper modelMapper;

    // Test for the methods getAllStudents() and getStudent(Long id)
    @Test
    public void shouldReturnAllStudentsOrStudentById() {
        //given
        Student student1 = new Student(1L, "Anna"
                , "Smirnova", "anna@ya.ru");
        Student student2 = new Student(2L, "Ivan"
                , "Ivanov", "ivan@gmail.com");
        List<Student> students = List.of(student1, student2);
        Mockito.when(studentRepository.findAll()).thenReturn(students);

        Mockito.when(modelMapper.map(students.get(0), StudentResponseDto.class))
                .thenReturn(new StudentResponseDto(1L, "Anna", "Smirnova"));
        Mockito.when(modelMapper.map(students.get(1), StudentResponseDto.class))
                .thenReturn(new StudentResponseDto(2L, "Ivan", "Ivanov"));

        StudentResponseDto studentResponseDto1 = modelMapper.map(students.get(0), StudentResponseDto.class);
        StudentResponseDto studentResponseDto2 = modelMapper.map(students.get(1), StudentResponseDto.class);
        List<StudentResponseDto> expectedStudentResponseDtos = List.of(studentResponseDto1, studentResponseDto2);

        //when
        List<StudentResponseDto> resultStudentResponseDtos = studentService.getAll();

        //then
        Assertions.assertEquals(2, resultStudentResponseDtos.size());
        Assertions.assertEquals(expectedStudentResponseDtos, resultStudentResponseDtos);
        Assertions.assertEquals(expectedStudentResponseDtos.get(0), (resultStudentResponseDtos.get(0)));
        Assertions.assertEquals(expectedStudentResponseDtos.get(1), (resultStudentResponseDtos.get(1)));
    }


    // Test for the method saveStudent(StudentRequestDto studentDto)
    @Test
    public void shouldSavedStudent() {
        //given
        Student student = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");

        StudentRequestDto studentRequestDto = new StudentRequestDto("Anna", "Smirnova", "anna@ya.ru");
        Mockito.when(modelMapper.map(studentRequestDto, Student.class)).thenReturn(student);

        Mockito.when(modelMapper.map(student, StudentResponseDto.class))
                .thenReturn(new StudentResponseDto(1L, "Anna", "Smirnova"));
        StudentResponseDto expectedStudentDto = modelMapper.map(student, StudentResponseDto.class);

        Mockito.when(studentRepository.save(student)).thenReturn(student);

        //when
        StudentResponseDto resultSavedStudentDto = studentService.save(studentRequestDto);

        //then
        Assertions.assertEquals(expectedStudentDto.getId(), resultSavedStudentDto.getId());
        Assertions.assertEquals(expectedStudentDto.getName(), resultSavedStudentDto.getName());
        Assertions.assertEquals(expectedStudentDto.getSurname(), resultSavedStudentDto.getSurname());
    }

    // Test for the method deleteStudent(Long id)
    @Test
    public void shouldDeleteStudentById() {
        //given
        Long studentId = 1L;
        Student student = new Student(studentId, "Anna"
                , "Smirnova", "anna@ya.ru");
        Optional<Student> optionalStudent = Optional.of(student);

        Mockito.when(studentRepository.findById(studentId)).thenReturn(optionalStudent);

        //when
        studentService.delete(studentId);

        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(studentRepository).findById(studentId);
        Mockito.verify(studentRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(studentId, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenStudentNotFound() {
        //given
        Long nonExistentStudentId = 10L;
        Mockito.when(studentRepository.findById(nonExistentStudentId)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> studentService.delete(nonExistentStudentId));

        //then
        Assertions.assertEquals("There is no student with id = "
                + nonExistentStudentId, exception.getMessage());
    }

    // Test for the method updateStudent(Long id, StudentUpdateDto updatedStudentDto)
    @Test
    public void shouldUpdateStudent() {
        //given
        Long studentId = 1L;
        Student existingStudent = new Student(studentId, "Anna"
                , "Smirnova", "anna@ya.ru");
        Optional<Student> optionalStudent = Optional.of(existingStudent);

        Mockito.when(studentRepository.findById(studentId)).thenReturn(optionalStudent);

        String updatedEmail = "updated_anna@ya.ru";
        StudentUpdateDto updatedStudentDto = new StudentUpdateDto();
        updatedStudentDto.setEmail(updatedEmail);

        Student updatedStudent = new Student(studentId, "Anna"
                , "Smirnova", updatedEmail);
        Mockito.when(studentRepository.save(existingStudent)).thenReturn(updatedStudent);
        Mockito.when(modelMapper.map(updatedStudent, StudentUpdateDto.class)).thenReturn(updatedStudentDto);

        //when
        StudentUpdateDto resultUpdatedStudentDto = studentService.update(studentId, updatedStudentDto);

        //then
        Assertions.assertEquals(updatedEmail, resultUpdatedStudentDto.getEmail());
        Mockito.verify(studentRepository).findById(studentId);
        Mockito.verify(studentRepository).save(existingStudent);
        Mockito.verify(modelMapper).map(updatedStudent, StudentUpdateDto.class);
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenUpdatingNonExistentStudent() {
        //given
        Long nonExistentStudentId = 10L;
        Mockito.when(studentRepository.findById(nonExistentStudentId)).thenReturn(Optional.empty());

        StudentUpdateDto updatedStudentDto = new StudentUpdateDto();
        updatedStudentDto.setEmail("updated_email@example.com");

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> studentService.update(nonExistentStudentId, updatedStudentDto));

        //then
        Assertions.assertEquals("There is no student with id = "
                + nonExistentStudentId, exception.getMessage());
    }

}
