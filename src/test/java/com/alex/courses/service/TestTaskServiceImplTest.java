package com.alex.courses.service;

import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.lessonDto.LessonResponseDto;
import com.alex.courses.dto.textTaskDto.TextTaskRequestDto;
import com.alex.courses.dto.textTaskDto.TextTaskResponseDto;
import com.alex.courses.dto.textTaskDto.TextTaskUpdateDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Lesson;
import com.alex.courses.entity.TextTask;
import com.alex.courses.exseption_handling.BindingAlreadyExistsException;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.LessonRepository;
import com.alex.courses.repository.TextTaskRepository;
import com.alex.courses.service.textTask.TextTaskServiceImpl;
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
public class TestTaskServiceImplTest {

    @InjectMocks
    private TextTaskServiceImpl textTaskService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TextTaskRepository textTaskRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Test
    public void shouldReturnAllTextTasksOrTextTaskById() {
        //given
        Course course = new Course(1L, "course1", null);
        Lesson lesson1 = new Lesson(1L, "lesson1", course);
        TextTask textTask1 = new TextTask(1L, "Task 1",  lesson1);

        Lesson lesson2 = new Lesson(2L, "lesson2", course);
        TextTask textTask2 = new TextTask(2L, "Task 2", lesson2);

        List<TextTask> textTasks = List.of(textTask1, textTask2);

        Mockito.when(textTaskRepository.findAll()).thenReturn(textTasks);

        Mockito.when(modelMapper.map(textTasks.get(0), TextTaskResponseDto.class))
                .thenReturn(new TextTaskResponseDto(1L, "Task 1"
                        ,new LessonResponseDto(1L, "lesson1"
                        , new CourseResponseDto(1L, "course1", null))));

        Mockito.when(modelMapper.map(textTasks.get(1), TextTaskResponseDto.class))
                .thenReturn(new TextTaskResponseDto(2L, "Task 2"
                        ,new LessonResponseDto(2L, "lesson2"
                        , new CourseResponseDto(1L, "course1", null))));

        TextTaskResponseDto responseDto1 = modelMapper.map(textTasks.get(0), TextTaskResponseDto.class);
        TextTaskResponseDto responseDto2 = modelMapper.map(textTasks.get(1), TextTaskResponseDto.class);

        List<TextTaskResponseDto> expectedResponseDtos = List.of(responseDto1, responseDto2);

        //when
        List<TextTaskResponseDto> resultResponseDtos = textTaskService.getAll();

        //then
        Assertions.assertEquals(expectedResponseDtos, resultResponseDtos);
        Assertions.assertEquals(expectedResponseDtos.get(0), resultResponseDtos.get(0));
        Assertions.assertEquals(expectedResponseDtos.get(1), resultResponseDtos.get(1));
        Assertions.assertEquals(expectedResponseDtos.get(1).getLesson(), resultResponseDtos.get(1).getLesson());
        Assertions.assertEquals(expectedResponseDtos.get(1).getTaskDescription(), resultResponseDtos.get(1).getTaskDescription());
    }

    @Test
    public void shouldSaveTextTask() {
        // given
        Long lessonId = 7L;
        String taskDescription = "Task Description";

        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);

        Mockito.when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));
        Mockito.when(textTaskRepository.findByLessonIdAndTaskDescription(lessonId, taskDescription)).thenReturn(Optional.empty());

        TextTask textTask = new TextTask(1L, taskDescription, lesson);
        TextTaskResponseDto expectedResponseDto = new TextTaskResponseDto(1L
                , taskDescription
                , new LessonResponseDto(1L, "lesson1"
                , new CourseResponseDto(1L, "course1", null)));

        Mockito.when(modelMapper.map(any(TextTask.class), eq(TextTaskResponseDto.class))).thenReturn(expectedResponseDto);
        Mockito.when(textTaskRepository.save(any(TextTask.class))).thenReturn(textTask);

        // when
        TextTaskRequestDto requestDto = new TextTaskRequestDto(taskDescription, lessonId);
        TextTaskResponseDto resultSavedResponseDto = textTaskService.save(requestDto);

        // then
        Assertions.assertEquals(expectedResponseDto.getId(), resultSavedResponseDto.getId());
        Assertions.assertEquals(expectedResponseDto.getLesson(), resultSavedResponseDto.getLesson());
        Assertions.assertEquals(expectedResponseDto.getTaskDescription(), resultSavedResponseDto.getTaskDescription());
    }

    @Test
    public void shouldThrowBindingAlreadyExistsExceptionWhenTextTaskAlreadyExistsInLesson() {
        //given
        Long lessonId = 7L;
        String taskDescription = "Existing Task Description";

        Course course = new Course(1L, "course1", null);
        Lesson existingLesson = new Lesson(lessonId, "lesson1", course);
        TextTask existingTextTask = new TextTask(1L, taskDescription, existingLesson);

        Mockito.when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(existingLesson));
        Mockito.when(textTaskRepository.findByLessonIdAndTaskDescription(lessonId, taskDescription)).thenReturn(Optional.of(existingTextTask));

        TextTaskRequestDto textTaskDto = new TextTaskRequestDto();
        textTaskDto.setLessonId(lessonId);
        textTaskDto.setTaskDescription(taskDescription);

        //when
        BindingAlreadyExistsException exception = Assertions.assertThrows(BindingAlreadyExistsException.class
                , () -> textTaskService.save(textTaskDto));

        //then
        Assertions.assertEquals("This text task already exists in this lesson", exception.getMessage());
    }

    @Test
    public void shouldUpdateTextTask() {
        //given
        Long textTaskId = 1L;
        Long updatedLessonId = 2L;
        String existingTaskDescription = "Task Description";

        Course course = new Course(1L, "course1", null);
        Lesson updatedLesson = new Lesson(updatedLessonId, "lesson2", course);
        TextTask existingTextTask = new TextTask(textTaskId, existingTaskDescription, new Lesson(1L, "lesson1", course));

        Mockito.when(textTaskRepository.findById(textTaskId)).thenReturn(Optional.of(existingTextTask));
        Mockito.when(lessonRepository.findById(updatedLessonId)).thenReturn(Optional.of(updatedLesson));
        Mockito.when(textTaskRepository.findByLessonIdAndTaskDescription(updatedLessonId, existingTaskDescription)).thenReturn(Optional.empty());

        TextTask updatedTextTask = new TextTask(textTaskId, existingTaskDescription, updatedLesson);
        Mockito.when(textTaskRepository.save(existingTextTask)).thenReturn(updatedTextTask);

        TextTaskResponseDto updatedTextTaskResponseDto = new TextTaskResponseDto(textTaskId
                , existingTaskDescription
                , new LessonResponseDto(updatedLessonId, "lesson2"
                , new CourseResponseDto(1L, "course1", null)));

        Mockito.when(modelMapper.map(updatedTextTask, TextTaskResponseDto.class))
                .thenReturn(updatedTextTaskResponseDto);

        TextTaskUpdateDto updatedTextTaskDto = new TextTaskUpdateDto();
        updatedTextTaskDto.setLessonId(updatedLessonId);

        //when
        TextTaskResponseDto resultUpdatedTextTaskDto = textTaskService.update(textTaskId, updatedTextTaskDto);

        //then
        Assertions.assertEquals(updatedLessonId, resultUpdatedTextTaskDto.getLesson().getId());
        Mockito.verify(textTaskRepository).findById(textTaskId);
        Mockito.verify(lessonRepository).findById(updatedLessonId);
        Mockito.verify(textTaskRepository).save(existingTextTask);
        Mockito.verify(modelMapper).map(updatedTextTask, TextTaskResponseDto.class);
    }

    @Test
    public void shouldDeleteTextTask() {
        // given
        Long existingTextTaskId = 1L;
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);
        TextTask existingTextTask = new TextTask(existingTextTaskId, "Task Description", lesson);

        Mockito.when(textTaskRepository.findById(existingTextTaskId)).thenReturn(Optional.of(existingTextTask));

        // when
        textTaskService.delete(existingTextTaskId);

        // then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(textTaskRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(existingTextTaskId, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenTextTaskNotFound() {
        //given
        Long nonExistentTextTaskId = 10L;
        Mockito.when(textTaskRepository.findById(nonExistentTextTaskId)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> textTaskService.delete(nonExistentTextTaskId));

        //then
        Assertions.assertEquals("There is no text task with id = " + nonExistentTextTaskId, exception.getMessage());
    }

}
