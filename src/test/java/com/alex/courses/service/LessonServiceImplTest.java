package com.alex.courses.service;

import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.lessonDto.LessonRequestDto;
import com.alex.courses.dto.lessonDto.LessonResponseDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Lesson;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.CourseRepository;
import com.alex.courses.repository.LessonRepository;
import com.alex.courses.service.lesson.LessonServiceImpl;
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
public class LessonServiceImplTest {

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CourseRepository courseRepository;

    @Test
    public void shouldReturnAllLessonsOrLessonById() {
        //given
        Course course = new Course(1L, "course1", null);
        Lesson lesson1 = new Lesson(1L, "lesson1", course);
        Lesson lesson2 = new Lesson(2L, "lesson2", course);
        List<Lesson> lessons = List.of(lesson1, lesson2);
        Mockito.when(lessonRepository.findAll()).thenReturn(lessons);

        Mockito.when(modelMapper.map(lessons.get(0), LessonResponseDto.class))
                .thenReturn(new LessonResponseDto(1L, "lesson1"
                        , new CourseResponseDto(1L, "course1", null)));
        Mockito.when(modelMapper.map(lessons.get(1), LessonResponseDto.class))
                .thenReturn(new LessonResponseDto(2L, "lesson2"
                        , new CourseResponseDto(1L, "course1", null)));

        LessonResponseDto responseDto1 = modelMapper.map(lessons.get(0), LessonResponseDto.class);
        LessonResponseDto responseDto2 = modelMapper.map(lessons.get(1), LessonResponseDto.class);
        List<LessonResponseDto> expectedResponseDtos = List.of(responseDto1, responseDto2);

        //when
        List<LessonResponseDto> resultResponseDtos = lessonService.getAll();

        //then
        Assertions.assertEquals(expectedResponseDtos, resultResponseDtos);
        Assertions.assertEquals(expectedResponseDtos.get(0), resultResponseDtos.get(0));
        Assertions.assertEquals(expectedResponseDtos.get(0).getCourse(), resultResponseDtos.get(0).getCourse());
        Assertions.assertEquals(expectedResponseDtos.get(1), resultResponseDtos.get(1));
        Assertions.assertEquals(expectedResponseDtos.get(1).getCourse(), resultResponseDtos.get(1).getCourse());
    }

    @Test
    public void shouldSaveLesson() {
        // given
        Long courseId = 2L;
        Course course = new Course(courseId, "course1", null);
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Lesson lesson = new Lesson(1L, "lesson1", course);
        LessonResponseDto expectedResponseDto = new LessonResponseDto(1L
                , "lesson1"
                , new CourseResponseDto(courseId, "course1", null));

        Mockito.when(modelMapper.map(any(Lesson.class), eq(LessonResponseDto.class))).thenReturn(expectedResponseDto);
        Mockito.when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);

        // when
        LessonRequestDto requestDto = new LessonRequestDto("lesson1", courseId);
        LessonResponseDto resultSavedResponseDto = lessonService.save(requestDto);

        // then
        Assertions.assertEquals(expectedResponseDto.getId(), resultSavedResponseDto.getId());
        Assertions.assertEquals(expectedResponseDto.getLessonName(), resultSavedResponseDto.getLessonName());
        Assertions.assertEquals(expectedResponseDto.getCourse(), resultSavedResponseDto.getCourse());
    }

    @Test
    public void shouldDeleteLesson() {
        //given
        Long existingLessonId = 1L;
        Lesson existingLesson = new Lesson(existingLessonId, "lesson1", new Course());
        Mockito.when(lessonRepository.findById(existingLessonId)).thenReturn(Optional.of(existingLesson));

        //when
        lessonService.delete(existingLessonId);

        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(lessonRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(existingLessonId, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenLessonNotFound() {
        //given
        Long nonExistentLessonId = 10L;
        Mockito.when(lessonRepository.findById(nonExistentLessonId)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> lessonService.delete(nonExistentLessonId));

        //then
        Assertions.assertEquals("There is no lesson with id = "
                + nonExistentLessonId, exception.getMessage());
    }


}
