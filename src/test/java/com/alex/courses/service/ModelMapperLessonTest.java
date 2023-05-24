package com.alex.courses.service;

import com.alex.courses.dto.lessonDto.LessonRequestDto;
import com.alex.courses.dto.lessonDto.LessonResponseDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Lesson;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.CourseRepository;
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
public class ModelMapperLessonTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Mock
    private CourseRepository courseRepository;


    @Test
    public void shouldMapLessonRequestDtoToLesson() {
        // given
        Course course = new Course(1L, "Course1", null);
        LessonRequestDto lessonDto = new LessonRequestDto("lesson1", course.getId());

        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        // when
        Course existingCourse = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no course with id = " + lessonDto.getCourseId()));

        Lesson lesson = new Lesson();
        lesson.setCourse(existingCourse);
        lesson.setLessonName(lessonDto.getLessonName());

        // then
        Assertions.assertEquals(lessonDto.getCourseId(), lesson.getCourse().getId());
        Assertions.assertEquals(lessonDto.getLessonName(), lesson.getLessonName());
    }

    @Test
    public void shouldMapLessonToLessonResponseDto() {
        //given
        String lessonName = "lesson1";
        Course course = new Course(2L, "Course1", null);
        Lesson lesson = new Lesson(3L,lessonName, course);

        //when
        LessonResponseDto lessonDto = modelMapper.map(lesson, LessonResponseDto.class);

        //then
        Assertions.assertEquals(lesson.getId(), lessonDto.getId());
        Assertions.assertEquals(lesson.getLessonName(), lessonDto.getLessonName());
        Assertions.assertEquals(lesson.getCourse().getId(), lessonDto.getCourse().getId());
        Assertions.assertEquals(lesson.getCourse().getName(), lessonDto.getCourse().getName());
    }
}

