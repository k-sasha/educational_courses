package com.alex.courses.service;

import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingRequestDto;
import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingResponseDto;
import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingUpdateDto;
import com.alex.courses.entity.Access;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Student;
import com.alex.courses.entity.StudentAccessBinding;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.AccessRepository;
import com.alex.courses.repository.CourseRepository;
import com.alex.courses.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ModelMapperStudentAccessBindingTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private AccessRepository accessRepository;

    @Test
    public void shouldMapStudentAccessBindingRequestDtoToStudentAccessBinding() {
        // given
        Student student = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(2L, "course1", null);
        Access access = new Access(3L, "standard", 5);

        StudentAccessBindingRequestDto studentAccessBindingDto = new StudentAccessBindingRequestDto();
        studentAccessBindingDto.setStudentId(1L);
        studentAccessBindingDto.setCourseId(2L);
        studentAccessBindingDto.setAccessId(3L);

        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(2L)).thenReturn(Optional.of(course));
        Mockito.when(accessRepository.findById(3L)).thenReturn(Optional.of(access));

        // when
        Student existingStudent = studentRepository.findById(studentAccessBindingDto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no student with id = " + studentAccessBindingDto.getStudentId()));

        Course existingCourse = courseRepository.findById(studentAccessBindingDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no course with id = " + studentAccessBindingDto.getCourseId()));

        Access existingAccess = accessRepository.findById(studentAccessBindingDto.getAccessId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no access with id = " + studentAccessBindingDto.getAccessId()));

        StudentAccessBinding studentAccessBinding = new StudentAccessBinding();
        studentAccessBinding.setStudent(existingStudent);
        studentAccessBinding.setCourse(existingCourse);
        studentAccessBinding.setAccess(existingAccess);

        // then
        Assertions.assertEquals(studentAccessBindingDto.getStudentId(), studentAccessBinding.getStudent().getId());
        Assertions.assertEquals(studentAccessBindingDto.getCourseId(), studentAccessBinding.getCourse().getId());
        Assertions.assertEquals(studentAccessBindingDto.getAccessId(), studentAccessBinding.getAccess().getId());
    }

    @Test
    public void shouldMapStudentAccessBindingToStudentAccessBindingResponseDto() {
        //given
        Student student = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(2L, "course1", null);
        Access access = new Access(3L, "standard", 5);
        StudentAccessBinding studentAccessBinding = new StudentAccessBinding(4L, student, course, access);

        //when
        StudentAccessBindingResponseDto studentAccessBindingDto = modelMapper.map(studentAccessBinding, StudentAccessBindingResponseDto.class);

        //then
        Assertions.assertEquals(studentAccessBinding.getId(), studentAccessBindingDto.getId());
        Assertions.assertEquals(studentAccessBinding.getStudent().getId(), studentAccessBindingDto.getStudent().getId());
        Assertions.assertEquals(studentAccessBinding.getCourse().getId(), studentAccessBindingDto.getCourse().getId());
        Assertions.assertEquals(studentAccessBinding.getAccess().getId(), studentAccessBindingDto.getAccess().getId());
    }

    @Test
    public void shouldMapStudentAccessBindingUpdateDtoToStudentAccessBinding() {
        // given
        Access access = new Access(3L, "standard", 5);
        StudentAccessBinding existingStudentAccessBinding = new StudentAccessBinding(4L, new Student(), new Course(), access);

        StudentAccessBindingUpdateDto studentAccessBindingUpdateDto = new StudentAccessBindingUpdateDto();
        studentAccessBindingUpdateDto.setAccessId(3L);

        Mockito.when(accessRepository.findById(3L)).thenReturn(Optional.of(access));

        // when
        Access existingAccess = accessRepository.findById(studentAccessBindingUpdateDto.getAccessId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no access with id = " + studentAccessBindingUpdateDto.getAccessId()));

        existingStudentAccessBinding.setAccess(existingAccess);

        // then
        Assertions.assertEquals(studentAccessBindingUpdateDto.getAccessId(), existingStudentAccessBinding.getAccess().getId());
    }

}

