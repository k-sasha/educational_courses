package com.alex.courses.service;

import com.alex.courses.dto.courseCuratorDto.CourseCuratorRequestDto;
import com.alex.courses.dto.courseCuratorDto.CourseCuratorResponseDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.CourseCurator;
import com.alex.courses.entity.Curator;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.CourseRepository;
import com.alex.courses.repository.CuratorRepository;
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
public class ModelMapperCourseCuratorTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CuratorRepository curatorRepository;

    @Test
    public void shouldMapCourseCuratorRequestDtoToCourseCurator() {
        // given
        Curator curator = new Curator(2L, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(1L, "Course1", null);

        CourseCuratorRequestDto courseCuratorDto = new CourseCuratorRequestDto();
        courseCuratorDto.setCourseId(1L);
        courseCuratorDto.setCuratorId(2L);

        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(curatorRepository.findById(2L)).thenReturn(Optional.of(curator));

        // when
        Course existingCourse = courseRepository.findById(courseCuratorDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no course with id = " + courseCuratorDto.getCourseId()));

        Curator existingCurator = curatorRepository.findById(courseCuratorDto.getCuratorId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no curator with id = " + courseCuratorDto.getCuratorId()));

        CourseCurator courseCurator = new CourseCurator();
        courseCurator.setCourse(existingCourse);
        courseCurator.setCurator(existingCurator);

        // then
        Assertions.assertEquals(courseCuratorDto.getCourseId(), courseCurator.getCourse().getId());
        Assertions.assertEquals(courseCuratorDto.getCuratorId(), courseCurator.getCurator().getId());
    }

    @Test
    public void shouldMapCourseCuratorToCourseCuratorResponseDto() {
        //given
        Curator curator = new Curator(1L, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(2L, "Course1", null);
        CourseCurator courseCurator = new CourseCurator(3L, course, curator);

        //when
        CourseCuratorResponseDto courseCuratorDto = modelMapper.map(courseCurator, CourseCuratorResponseDto.class);

        //then
        Assertions.assertEquals(courseCurator.getId(), courseCuratorDto.getId());
        Assertions.assertEquals(courseCurator.getCourse().getId(), courseCuratorDto.getCourse().getId());
        Assertions.assertEquals(courseCurator.getCourse().getName(), courseCuratorDto.getCourse().getName());
        Assertions.assertEquals(courseCurator.getCurator().getId(), courseCuratorDto.getCurator().getId());
        Assertions.assertEquals(courseCurator.getCurator().getName(), courseCuratorDto.getCurator().getName());
        Assertions.assertEquals(courseCurator.getCurator().getSurname(), courseCuratorDto.getCurator().getSurname());
    }
}

