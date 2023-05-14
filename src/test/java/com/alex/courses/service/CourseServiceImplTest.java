package com.alex.courses.service;

import com.alex.courses.dto.courseDto.CourseRequestDto;
import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.entity.Course;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.CourseRepository;
import com.alex.courses.service.course.CourseServiceImpl;
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
public class CourseServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CourseRepository courseRepository;


    @InjectMocks //i.e. upload this mocks (adminRepository and modelMapper)
    // to the service (adminService)
    private CourseServiceImpl courseService;

    // Test for the methods getAllCourses() and getCourse(Long id)
    @Test
    public void shouldReturnAllAdminsOrAdminById() {
        //given
        Course course1 = new Course(1L, "course1", null);
        Course course2 = new Course(2L, "course2", null);
        List<Course> courses = List.of(course1, course2);
        Mockito.when(courseRepository.findAll()).thenReturn(courses);

        Mockito.when(modelMapper.map(courses.get(0), CourseResponseDto.class))
                .thenReturn(new CourseResponseDto(1L, "course1", null));
        Mockito.when(modelMapper.map(courses.get(1), CourseResponseDto.class))
                .thenReturn(new CourseResponseDto(2L, "course2", null));

        CourseResponseDto courseResponseDto1 = modelMapper.map(courses.get(0), CourseResponseDto.class);
        CourseResponseDto courseResponseDto2 = modelMapper.map(courses.get(1), CourseResponseDto.class);
        List<CourseResponseDto> expectedCourseResponseDtos = List.of(courseResponseDto1, courseResponseDto2);

        //when
        List<CourseResponseDto> resultCourseResponseDtos = courseService.getAllCourses();

        //then
//        Assertions.assertEquals(2, resultCourseResponseDtos.size());
        Assertions.assertEquals(expectedCourseResponseDtos, resultCourseResponseDtos);
        Assertions.assertEquals(expectedCourseResponseDtos.get(0), (resultCourseResponseDtos.get(0)));
        Assertions.assertEquals(expectedCourseResponseDtos.get(1), (resultCourseResponseDtos.get(1)));
    }

    // Test for the method saveCourse (CourseRequestDto courseDto)
    @Test
    public void shouldSavedCourse() {
        //given
        Course course = new Course(1L, "course1", null);

        CourseRequestDto courseRequestDto = new CourseRequestDto("course1", null);
        Mockito.when(modelMapper.map(courseRequestDto, Course.class)).thenReturn(course);

        Mockito.when(modelMapper.map(course, CourseResponseDto.class))
                .thenReturn(new CourseResponseDto(1L, "course1", null));
        CourseResponseDto expectedCourseDto = modelMapper.map(course, CourseResponseDto.class);

        Mockito.when(courseRepository.save(course)).thenReturn(course);

        //when
        CourseResponseDto resultSavedCourseDto = courseService.saveCourse(courseRequestDto);

        //then
        Assertions.assertEquals(expectedCourseDto.getName(), resultSavedCourseDto.getName());
        Assertions.assertEquals(expectedCourseDto.getAdmin(), resultSavedCourseDto.getAdmin());
    }

    // Test for the method deleteCourse(Long id)
    @Test
    public void shouldDeleteCourseById() {
        //given
        Long courseId = 1L;
        Course course = new Course(courseId, "course1", null);
        Optional<Course> optionalCourse = Optional.of(course);

        Mockito.when(courseRepository.findById(courseId)).thenReturn(optionalCourse);

        //when
        courseService.deleteCourse(courseId);

        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(courseRepository).findById(courseId);
        Mockito.verify(courseRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(courseId, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenCourseNotFound() {
        //given
        Long nonExistentCourseId = 10L;
        Mockito.when(courseRepository.findById(nonExistentCourseId)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> courseService.deleteCourse(nonExistentCourseId));

        //then
        Assertions.assertEquals("There is no course with id = "
                + nonExistentCourseId, exception.getMessage());
    }
}
