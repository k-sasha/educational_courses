package com.alex.courses.service;

import com.alex.courses.dto.accessToLessonDto.AccessToLessonRequestDto;
import com.alex.courses.dto.accessToLessonDto.AccessToLessonResponseDto;
import com.alex.courses.entity.Access;
import com.alex.courses.entity.AccessToLesson;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Lesson;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.AccessRepository;
import com.alex.courses.repository.LessonRepository;
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
public class ModelMapperAccessToLessonTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private AccessRepository accessRepository;

    @Test
    public void shouldMapAccessToLessonRequestDtoToAccessToLesson() {
        // given
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);
        Access access = new Access(2L, "vip", 12);

        AccessToLessonRequestDto accessToLessonDto = new AccessToLessonRequestDto();
        accessToLessonDto.setLessonId(1L);
        accessToLessonDto.setAccessId(2L);

        Mockito.when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        Mockito.when(accessRepository.findById(2L)).thenReturn(Optional.of(access));

        // when
        Lesson existingLesson = lessonRepository.findById(accessToLessonDto.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no lesson with id = " + accessToLessonDto.getLessonId()));

        Access existingAccess = accessRepository.findById(accessToLessonDto.getAccessId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no access with id = " + accessToLessonDto.getAccessId()));

        AccessToLesson accessToLesson = new AccessToLesson();
        accessToLesson.setLesson(existingLesson);
        accessToLesson.setAccess(existingAccess);

        // then
        Assertions.assertEquals(accessToLessonDto.getLessonId(), accessToLesson.getLesson().getId());
        Assertions.assertEquals(accessToLessonDto.getAccessId(), accessToLesson.getAccess().getId());
    }

    @Test
    public void shouldMapAccessToLessonToAccessToLessonResponseDto() {
        //given
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);
        Access access = new Access(2L, "vip", 12);
        AccessToLesson accessToLesson = new AccessToLesson(3L, lesson, access);

        //when
        AccessToLessonResponseDto accessToLessonDto = modelMapper.map(accessToLesson, AccessToLessonResponseDto.class);

        //then
        Assertions.assertEquals(accessToLesson.getId(), accessToLessonDto.getId());
        Assertions.assertEquals(accessToLesson.getLesson().getId(), accessToLessonDto.getLesson().getId());
        Assertions.assertEquals(accessToLesson.getAccess().getId(), accessToLessonDto.getAccess().getId());
    }
}
