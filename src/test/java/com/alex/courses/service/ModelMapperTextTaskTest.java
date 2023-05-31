package com.alex.courses.service;

import com.alex.courses.dto.textTaskDto.TextTaskRequestDto;
import com.alex.courses.dto.textTaskDto.TextTaskResponseDto;
import com.alex.courses.dto.textTaskDto.TextTaskUpdateDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Lesson;
import com.alex.courses.entity.TextTask;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
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
public class ModelMapperTextTaskTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Mock
    private LessonRepository lessonRepository;

    @Test
    public void shouldMapTextTaskRequestDtoToTextTask() {
        // given
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);
        String taskDescription = "Text task description";

        TextTaskRequestDto textTaskDto = new TextTaskRequestDto();
        textTaskDto.setLessonId(1L);
        textTaskDto.setTaskDescription(taskDescription);

        Mockito.when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));

        // when
        Lesson existingLesson = lessonRepository.findById(textTaskDto.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no lesson with id = " + textTaskDto.getLessonId()));

        TextTask textTask = new TextTask();
        textTask.setLesson(existingLesson);
        textTask.setTaskDescription(textTaskDto.getTaskDescription());

        // then
        Assertions.assertEquals(textTaskDto.getLessonId(), textTask.getLesson().getId());
        Assertions.assertEquals(textTaskDto.getTaskDescription(), textTask.getTaskDescription());
    }

    @Test
    public void shouldMapTextTaskToTextTaskResponseDto() {
        //given
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);
        String taskDescription = "Text task description";
        TextTask textTask = new TextTask(2L, taskDescription, lesson);

        //when
        TextTaskResponseDto textTaskDto = modelMapper.map(textTask, TextTaskResponseDto.class);

        //then
        Assertions.assertEquals(textTask.getId(), textTaskDto.getId());
        Assertions.assertEquals(textTask.getLesson().getId(), textTaskDto.getLesson().getId());
        Assertions.assertEquals(textTask.getTaskDescription(), textTaskDto.getTaskDescription());
    }

    @Test
    public void shouldMapTextTaskUpdateDtoToTextTask() {
        // given
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(3L, "lesson3", course);
        String taskDescription = "Existing text task description";
        TextTask existingTextTask = new TextTask(4L, taskDescription, lesson);

        TextTaskUpdateDto textTaskUpdateDto = new TextTaskUpdateDto();
        textTaskUpdateDto.setLessonId(3L);

        Mockito.when(lessonRepository.findById(3L)).thenReturn(Optional.of(lesson));

        // when
        Lesson existingLesson = lessonRepository.findById(textTaskUpdateDto.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no lesson with id = " + textTaskUpdateDto.getLessonId()));

        existingTextTask.setLesson(existingLesson);

        // then
        Assertions.assertEquals(textTaskUpdateDto.getLessonId(), existingTextTask.getLesson().getId());
    }

}
