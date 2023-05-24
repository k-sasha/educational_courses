package com.alex.courses.service;

import com.alex.courses.dto.accessDto.AccessResponseDto;
import com.alex.courses.dto.accessToLessonDto.AccessToLessonRequestDto;
import com.alex.courses.dto.accessToLessonDto.AccessToLessonResponseDto;
import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.lessonDto.LessonResponseDto;
import com.alex.courses.entity.Access;
import com.alex.courses.entity.AccessToLesson;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Lesson;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.AccessRepository;
import com.alex.courses.repository.AccessToLessonRepository;
import com.alex.courses.repository.LessonRepository;
import com.alex.courses.service.accessToLesson.AccessToLessonServiceImpl;
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
public class AccessToLessonServiceImplTest {

    @InjectMocks
    private AccessToLessonServiceImpl accessToLessonService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AccessToLessonRepository accessToLessonRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private AccessRepository accessRepository;

    @Test
    public void shouldReturnAllAccessToLessonsOrAccessToLessonById() {
        //given
        Course course = new Course(1L, "course1", null);
        Lesson lesson1 = new Lesson(1L, "lesson1", course);
        Access access1 = new Access(1L, "standart", 5);
        Lesson lesson2 = new Lesson(2L, "lesson2", course);
        Access access2 = new Access(2L, "business", 10);
        AccessToLesson accessToLesson1 = new AccessToLesson(1L, lesson1, access1);
        AccessToLesson accessToLesson2 = new AccessToLesson(2L, lesson2, access2);
        List<AccessToLesson> accessToLessons = List.of(accessToLesson1, accessToLesson2);
        Mockito.when(accessToLessonRepository.findAll()).thenReturn(accessToLessons);

        Mockito.when(modelMapper.map(accessToLessons.get(0), AccessToLessonResponseDto.class))
                .thenReturn(new AccessToLessonResponseDto(1L
                        , new LessonResponseDto(1L, "lesson1"
                        , new CourseResponseDto(1L, "course1", null) )
                        , new AccessResponseDto(1L, "standart", 5)));
        Mockito.when(modelMapper.map(accessToLessons.get(1), AccessToLessonResponseDto.class))
                .thenReturn(new AccessToLessonResponseDto(2L
                        , new LessonResponseDto(2L, "lesson1"
                        , new CourseResponseDto(1L, "course1", null) )
                        , new AccessResponseDto(2L, "standart", 5)));

        AccessToLessonResponseDto responseDto1 = modelMapper.map(accessToLessons.get(0), AccessToLessonResponseDto.class);
        AccessToLessonResponseDto responseDto2 = modelMapper.map(accessToLessons.get(1), AccessToLessonResponseDto.class);
        List<AccessToLessonResponseDto> expectedResponseDtos = List.of(responseDto1, responseDto2);

        //when
        List<AccessToLessonResponseDto> resultResponseDtos = accessToLessonService.getAll();

        //then
        Assertions.assertEquals(expectedResponseDtos, resultResponseDtos);
        Assertions.assertEquals(expectedResponseDtos.get(0), resultResponseDtos.get(0));
        Assertions.assertEquals(expectedResponseDtos.get(1), resultResponseDtos.get(1));
        Assertions.assertEquals(expectedResponseDtos.get(1).getLesson(), resultResponseDtos.get(1).getLesson());
        Assertions.assertEquals(expectedResponseDtos.get(1).getAccess(), resultResponseDtos.get(1).getAccess());
    }


    @Test
    public void shouldSaveAccessToLesson() {
        // given
        Long lessonId = 7L;
        Long accessId = 3L;

        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(lessonId, "lesson1", course);
        Access access = new Access(accessId, "vip", 12);

        Mockito.when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));
        Mockito.when(accessRepository.findById(accessId)).thenReturn(Optional.of(access));

        AccessToLesson accessToLesson = new AccessToLesson(1L, lesson, access);
        AccessToLessonResponseDto expectedResponseDto = new AccessToLessonResponseDto(1L
                , new LessonResponseDto(lessonId, "lesson1", new CourseResponseDto(1L, "course1", null) )
                , new AccessResponseDto(accessId, "vip", 12));

        Mockito.when(modelMapper.map(any(AccessToLesson.class), eq(AccessToLessonResponseDto.class))).thenReturn(expectedResponseDto);
        Mockito.when(accessToLessonRepository.save(any(AccessToLesson.class))).thenReturn(accessToLesson);

        // when
        AccessToLessonRequestDto requestDto = new AccessToLessonRequestDto(lessonId, accessId);
        AccessToLessonResponseDto resultSavedResponseDto = accessToLessonService.save(requestDto);

        // then
        Assertions.assertEquals(expectedResponseDto.getId(), resultSavedResponseDto.getId());
        Assertions.assertEquals(expectedResponseDto.getLesson(), resultSavedResponseDto.getLesson());
        Assertions.assertEquals(expectedResponseDto.getAccess(), resultSavedResponseDto.getAccess());
    }

    @Test
    public void shouldUpdateAccessToLesson() {
        // given
        Long lessonId = 7L;
        Long accessId = 3L;

        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(lessonId, "lesson1", course);
        Access oldAccess = new Access(2L, "business", 10);
        Access newAccess = new Access(accessId, "vip", 12);

        AccessToLesson existingAccessToLesson = new AccessToLesson(1L, lesson, oldAccess);

        Mockito.when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));
        Mockito.when(accessRepository.findById(accessId)).thenReturn(Optional.of(newAccess));
        Mockito.when(accessToLessonRepository.findByLessonId(lessonId)).thenReturn(Optional.of(existingAccessToLesson));

        AccessToLessonResponseDto expectedResponseDto = new AccessToLessonResponseDto(1L
                , new LessonResponseDto(lessonId, "lesson1", new CourseResponseDto(1L, "course1", null) )
                , new AccessResponseDto(accessId, "vip", 12));

        Mockito.when(modelMapper.map(any(AccessToLesson.class), eq(AccessToLessonResponseDto.class))).thenReturn(expectedResponseDto);
        Mockito.when(accessToLessonRepository.save(any(AccessToLesson.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        AccessToLessonRequestDto requestDto = new AccessToLessonRequestDto(lessonId, accessId);
        AccessToLessonResponseDto resultUpdatedResponseDto = accessToLessonService.save(requestDto);

        // then
        Assertions.assertEquals(expectedResponseDto.getId(), resultUpdatedResponseDto.getId());
        Assertions.assertEquals(expectedResponseDto.getLesson(), resultUpdatedResponseDto.getLesson());
        Assertions.assertEquals(expectedResponseDto.getAccess(), resultUpdatedResponseDto.getAccess());
    }

    @Test
    public void shouldDeleteAccessToLesson() {
        // given
        Long existingAccessToLessonId = 1L;
        AccessToLesson existingAccessToLesson = new AccessToLesson(existingAccessToLessonId, new Lesson(), new Access());
        Mockito.when(accessToLessonRepository.findById(existingAccessToLessonId)).thenReturn(Optional.of(existingAccessToLesson));

        // when
        accessToLessonService.delete(existingAccessToLessonId);

        // then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(accessToLessonRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(existingAccessToLessonId, argumentCaptor.getValue());
    }


    @Test
    public void shouldThrowResourceNotFoundExceptionWhenAccessToLessonNotFound() {
        //given
        Long nonExistentAccessToLessonId = 10L;
        Mockito.when(accessToLessonRepository.findById(nonExistentAccessToLessonId)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> accessToLessonService.delete(nonExistentAccessToLessonId));

        //then
        Assertions.assertEquals("There is no access-to-lesson with id = "
                + nonExistentAccessToLessonId, exception.getMessage());
    }


}
