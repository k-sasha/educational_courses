package com.alex.courses.service;


import com.alex.courses.dto.accessDto.AccessResponseDto;
import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingRequestDto;
import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingResponseDto;
import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingUpdateDto;
import com.alex.courses.dto.studentDto.StudentResponseDto;
import com.alex.courses.entity.Access;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Student;
import com.alex.courses.entity.StudentAccessBinding;
import com.alex.courses.exseption_handling.BindingAlreadyExistsException;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.AccessRepository;

import com.alex.courses.repository.CourseRepository;
import com.alex.courses.repository.StudentAccessBindingRepository;
import com.alex.courses.repository.StudentRepository;
import com.alex.courses.service.studentAccessBinding.StudentAccessBindingServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class StudentAccessBindingServiceImplTest {


    @InjectMocks
    private StudentAccessBindingServiceImpl studentAccessBindingService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private StudentAccessBindingRepository studentAccessBindingRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private AccessRepository accessRepository;

    @Test
    public void shouldReturnAllStudentAccessBindingsOrStudentAccessBindingById() {
        //given
        Student student1 = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        Course course1 = new Course(1L, "course1", null);
        Access access1 = new Access(1L, "standard", 5);
        StudentAccessBinding studentAccessBinding1 = new StudentAccessBinding(1L, student1, course1, access1);

        Student student2 = new Student(2L, "Ivan"
                , "Ivanov", "ivan@gmail.com");
        Course course2 = new Course(2L, "course2", null);
        Access access2 = new Access(2L, "business", 10);
        StudentAccessBinding studentAccessBinding2 = new StudentAccessBinding(2L, student2, course2, access2);

        List<StudentAccessBinding> studentAccessBindings = List.of(studentAccessBinding1, studentAccessBinding2);
        Mockito.when(studentAccessBindingRepository.findAll()).thenReturn(studentAccessBindings);

        Mockito.when(modelMapper.map(studentAccessBindings.get(0), StudentAccessBindingResponseDto.class))
                .thenReturn(new StudentAccessBindingResponseDto(1L
                        , new StudentResponseDto(1L, "Anna", "Smirnova")
                        , new CourseResponseDto(1L, "course1", null)
                        , new AccessResponseDto(1L, "standard", 5)));
        Mockito.when(modelMapper.map(studentAccessBindings.get(1), StudentAccessBindingResponseDto.class))
                .thenReturn(new StudentAccessBindingResponseDto(2L
                        , new StudentResponseDto(2L, "Ivan", "Ivanov")
                        , new CourseResponseDto(2L, "course2", null)
                        , new AccessResponseDto(2L, "business", 10)));

        StudentAccessBindingResponseDto responseDto1 = modelMapper.map(studentAccessBindings.get(0), StudentAccessBindingResponseDto.class);
        StudentAccessBindingResponseDto responseDto2 = modelMapper.map(studentAccessBindings.get(1), StudentAccessBindingResponseDto.class);
        List<StudentAccessBindingResponseDto> expectedResponseDtos = List.of(responseDto1, responseDto2);

        //when
        List<StudentAccessBindingResponseDto> resultResponseDtos = studentAccessBindingService.getAll();

        //then
        Assertions.assertEquals(expectedResponseDtos, resultResponseDtos);
        Assertions.assertEquals(expectedResponseDtos.get(0), resultResponseDtos.get(0));
        Assertions.assertEquals(expectedResponseDtos.get(1), resultResponseDtos.get(1));
        Assertions.assertEquals(expectedResponseDtos.get(1).getStudent(), resultResponseDtos.get(1).getStudent());
        Assertions.assertEquals(expectedResponseDtos.get(1).getCourse(), resultResponseDtos.get(1).getCourse());
        Assertions.assertEquals(expectedResponseDtos.get(1).getAccess(), resultResponseDtos.get(1).getAccess());
    }

    @Test
    public void shouldSaveStudentAccessBinding() {
        // given
        Long studentId = 7L;
        Long courseId = 3L;
        Long accessId = 1L;

        Student student = new Student(studentId, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(courseId, "course1", null);
        Access access = new Access(accessId, "vip", 12);

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        Mockito.when(accessRepository.findById(accessId)).thenReturn(Optional.of(access));
        Mockito.when(studentAccessBindingRepository.findByStudentIdAndCourseId(studentId, courseId)).thenReturn(Optional.empty());

        StudentAccessBinding studentAccessBinding = new StudentAccessBinding(1L, student, course, access);
        StudentAccessBindingResponseDto expectedResponseDto = new StudentAccessBindingResponseDto(1L
                , new StudentResponseDto(1L, "Anna", "Smirnova")
                , new CourseResponseDto(1L, "course1", null)
                , new AccessResponseDto(1L, "vip", 12));

        Mockito.when(modelMapper.map(any(StudentAccessBinding.class), eq(StudentAccessBindingResponseDto.class))).thenReturn(expectedResponseDto);
        Mockito.when(studentAccessBindingRepository.save(any(StudentAccessBinding.class))).thenReturn(studentAccessBinding);

        // when
        StudentAccessBindingRequestDto requestDto = new StudentAccessBindingRequestDto(studentId, courseId, accessId);
        StudentAccessBindingResponseDto resultSavedResponseDto = studentAccessBindingService.save(requestDto);

        // then
        Assertions.assertEquals(expectedResponseDto.getId(), resultSavedResponseDto.getId());
        Assertions.assertEquals(expectedResponseDto.getStudent(), resultSavedResponseDto.getStudent());
        Assertions.assertEquals(expectedResponseDto.getCourse(), resultSavedResponseDto.getCourse());
        Assertions.assertEquals(expectedResponseDto.getAccess(), resultSavedResponseDto.getAccess());
    }

    @Test
    public void shouldThrowBindingAlreadyExistsExceptionWhenStudentIsAlreadyAssignedToCourse() {
        //given
        Long studentId = 7L;
        Long courseId = 3L;
        Long accessId = 1L;

        Student student = new Student(studentId, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(courseId, "course1", null);
        Access access = new Access(accessId, "vip", 12);

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        Mockito.when(accessRepository.findById(accessId)).thenReturn(Optional.of(access));

        StudentAccessBinding existingStudentAccessBinding = new StudentAccessBinding(1L, student, course, access);
        Mockito.when(studentAccessBindingRepository.findByStudentIdAndCourseId(studentId, courseId)).thenReturn(Optional.of(existingStudentAccessBinding));

        //when
        StudentAccessBindingRequestDto requestDto = new StudentAccessBindingRequestDto(studentId, courseId, accessId);
        BindingAlreadyExistsException exception = Assertions.assertThrows(BindingAlreadyExistsException.class
                , () -> studentAccessBindingService.save(requestDto));

        //then
        Assertions.assertEquals("The student is already assigned to the course", exception.getMessage());
    }

    @Test
    public void shouldUpdateStudentAccessBinding() {
        //given
        Long studentAccessBindingId = 1L;
        Long updatedAccessId = 2L;

        Student student = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(1L, "course1", null);
        Access access = new Access(1L, "standard", 5);
        Access updatedAccess = new Access(updatedAccessId, "business", 10);

        StudentAccessBinding existingStudentAccessBinding = new StudentAccessBinding(studentAccessBindingId, student, course, access);
        Optional<StudentAccessBinding> optionalStudentAccessBinding = Optional.of(existingStudentAccessBinding);
        Mockito.when(studentAccessBindingRepository.findById(studentAccessBindingId)).thenReturn(optionalStudentAccessBinding);

        Mockito.when(accessRepository.findById(updatedAccessId)).thenReturn(Optional.of(updatedAccess));

        StudentAccessBinding updatedStudentAccessBinding = new StudentAccessBinding(studentAccessBindingId, student, course, updatedAccess);
        Mockito.when(studentAccessBindingRepository.save(existingStudentAccessBinding)).thenReturn(updatedStudentAccessBinding);

        StudentAccessBindingResponseDto updatedStudentAccessBindingResponseDto = new StudentAccessBindingResponseDto();
        updatedStudentAccessBindingResponseDto.setId(studentAccessBindingId);
        updatedStudentAccessBindingResponseDto.setAccess(new AccessResponseDto(updatedAccessId, "business", 10));

        Mockito.when(modelMapper.map(updatedStudentAccessBinding, StudentAccessBindingResponseDto.class))
                .thenReturn(updatedStudentAccessBindingResponseDto);

        StudentAccessBindingUpdateDto updatedStudentAccessBindingDto = new StudentAccessBindingUpdateDto();
        updatedStudentAccessBindingDto.setAccessId(updatedAccessId);

        //when
        StudentAccessBindingResponseDto resultUpdatedStudentAccessBindingDto = studentAccessBindingService.update(studentAccessBindingId, updatedStudentAccessBindingDto);

        //then
        Assertions.assertEquals(updatedAccessId, resultUpdatedStudentAccessBindingDto.getAccess().getId());
        Mockito.verify(studentAccessBindingRepository).findById(studentAccessBindingId);
        Mockito.verify(accessRepository).findById(updatedAccessId);
        Mockito.verify(studentAccessBindingRepository).save(existingStudentAccessBinding);
        Mockito.verify(modelMapper).map(updatedStudentAccessBinding, StudentAccessBindingResponseDto.class);
    }

    @Test
    public void shouldDeleteStudentAccessBinding() {
        // given
        Long existingStudentAccessBindingId = 1L;
        Student student = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(1L, "course1", null);
        Access access = new Access(1L, "standard", 5);

        StudentAccessBinding existingStudentAccessBinding = new StudentAccessBinding(existingStudentAccessBindingId, student, course, access);
        Mockito.when(studentAccessBindingRepository.findById(existingStudentAccessBindingId)).thenReturn(Optional.of(existingStudentAccessBinding));

        // when
        studentAccessBindingService.delete(existingStudentAccessBindingId);

        // then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(studentAccessBindingRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(existingStudentAccessBindingId, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenStudentAccessBindingNotFound() {
        //given
        Long nonExistentStudentAccessBindingId = 10L;
        Mockito.when(studentAccessBindingRepository.findById(nonExistentStudentAccessBindingId)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> studentAccessBindingService.delete(nonExistentStudentAccessBindingId));

        //then
        Assertions.assertEquals("There is no student access binding with id = "
                + nonExistentStudentAccessBindingId, exception.getMessage());
    }

}
