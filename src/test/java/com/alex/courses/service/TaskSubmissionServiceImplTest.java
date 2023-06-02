package com.alex.courses.service;

import com.alex.courses.dto.taskSubmissionDto.TaskSubmissionRequestDto;
import com.alex.courses.dto.taskSubmissionDto.TaskSubmissionResponseDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Curator;
import com.alex.courses.entity.Lesson;
import com.alex.courses.entity.Student;
import com.alex.courses.entity.TaskSubmission;
import com.alex.courses.entity.TextTask;
import com.alex.courses.entity.SubmissionType;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.StudentRepository;
import com.alex.courses.repository.TaskSubmissionRepository;
import com.alex.courses.repository.TextTaskRepository;
import com.alex.courses.repository.CuratorRepository;
import com.alex.courses.service.taskSubmission.TaskSubmissionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class TaskSubmissionServiceImplTest {

    @InjectMocks
    private TaskSubmissionServiceImpl taskSubmissionService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TaskSubmissionRepository taskSubmissionRepository;

    @Mock
    private TextTaskRepository textTaskRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CuratorRepository curatorRepository;

    @Test
    public void shouldReturnAllTaskSubmissions() {
        //given
        Course course = new Course(1L, "course1", null);
        Lesson lesson1 = new Lesson(1L, "lesson1", course);
        Curator curator1 = new Curator(1L, "Anna", "Smirnova", "anna@ya.ru");
        TextTask task1 = new TextTask(1L, "Task 1", lesson1);
        Student student1 = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        LocalDateTime localDateTime = LocalDateTime.now();
        TaskSubmission taskSubmission1 = new TaskSubmission(1L, task1, student1, curator1
                , "solution1", SubmissionType.STUDENT_SUBMISSION, localDateTime);
        TaskSubmission taskSubmission2 = new TaskSubmission(2L, task1, student1, curator1
                , "ok", SubmissionType.CURATOR_FEEDBACK, localDateTime);

        List<TaskSubmission> taskSubmissions = List.of(taskSubmission1, taskSubmission2);
        Mockito.when(taskSubmissionRepository.findAll()).thenReturn(taskSubmissions);

        TaskSubmissionResponseDto responseDto1 = new TaskSubmissionResponseDto(
                1L, task1.getId(), student1.getId(), curator1.getId(),
                "solution1", SubmissionType.STUDENT_SUBMISSION, localDateTime);
        Mockito.when(modelMapper.map(taskSubmissions.get(0), TaskSubmissionResponseDto.class))
                .thenReturn(responseDto1);

        TaskSubmissionResponseDto responseDto2 = new TaskSubmissionResponseDto(2L
                , task1.getId(), student1.getId(), curator1.getId(),
                "ok", SubmissionType.CURATOR_FEEDBACK, localDateTime);
        Mockito.when(modelMapper.map(taskSubmissions.get(1), TaskSubmissionResponseDto.class))
                .thenReturn(responseDto2);

        List<TaskSubmissionResponseDto> expectedResponseDtos = List.of(responseDto1, responseDto2);

        //when
        List<TaskSubmissionResponseDto> resultResponseDtos = taskSubmissionService.getAll();

        //then
        Assertions.assertEquals(expectedResponseDtos, resultResponseDtos);
        Assertions.assertEquals(expectedResponseDtos.get(0), resultResponseDtos.get(0));
        Assertions.assertEquals(expectedResponseDtos.get(1), resultResponseDtos.get(1));
        Assertions.assertEquals(expectedResponseDtos.get(1).getId(), resultResponseDtos.get(1).getId());
        Assertions.assertEquals(expectedResponseDtos.get(1).getSubmissionText(), resultResponseDtos.get(1).getSubmissionText());
        Assertions.assertEquals(expectedResponseDtos.get(1).getSubmissionType(), resultResponseDtos.get(1).getSubmissionType());
    }

    @Test
    public void shouldSaveTaskSubmission() {
        // given
        Long textTaskId = 1L;
        Long studentId = 1L;
        Long curatorId = 1L;
        String submissionText = "solution";
        SubmissionType submissionType = SubmissionType.STUDENT_SUBMISSION;

        TextTask textTask = new TextTask(textTaskId, "Task 1", null);
        Student student = new Student(studentId, "Anna", "Smirnova", "anna@ya.ru");
        Curator curator = new Curator(curatorId, "Maria", "Petrova", "maria@ya.ru");

        Mockito.when(textTaskRepository.findById(textTaskId)).thenReturn(Optional.of(textTask));
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(curatorRepository.findById(curatorId)).thenReturn(Optional.of(curator));

        TaskSubmission taskSubmission = new TaskSubmission();
        taskSubmission.setTextTask(textTask);
        taskSubmission.setStudent(student);
        taskSubmission.setCurator(curator);
        taskSubmission.setSubmissionText(submissionText);
        taskSubmission.setSubmissionType(submissionType);

        TaskSubmissionResponseDto expectedResponseDto = new TaskSubmissionResponseDto(null
                , textTaskId, studentId, curatorId, submissionText, submissionType, null);

        Mockito.when(modelMapper.map(any(TaskSubmission.class), eq(TaskSubmissionResponseDto.class))).thenReturn(expectedResponseDto);
        Mockito.when(taskSubmissionRepository.save(any(TaskSubmission.class))).thenReturn(taskSubmission);

        // when
        TaskSubmissionRequestDto requestDto = new TaskSubmissionRequestDto(textTaskId, studentId, curatorId, submissionText, submissionType);
        TaskSubmissionResponseDto resultSavedResponseDto = taskSubmissionService.save(requestDto);

        // then
        Assertions.assertEquals(expectedResponseDto.getTextTaskId(), resultSavedResponseDto.getTextTaskId());
        Assertions.assertEquals(expectedResponseDto.getStudentId(), resultSavedResponseDto.getStudentId());
        Assertions.assertEquals(expectedResponseDto.getCuratorId(), resultSavedResponseDto.getCuratorId());
        Assertions.assertEquals(expectedResponseDto.getSubmissionText(), resultSavedResponseDto.getSubmissionText());
        Assertions.assertEquals(expectedResponseDto.getSubmissionType(), resultSavedResponseDto.getSubmissionType());
    }

    @Test
    public void shouldDeleteTaskSubmission() {
        // given
        Long existingTaskSubmissionId = 1L;
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);
        Curator curator = new Curator(1L, "Anna", "Smirnova", "anna@ya.ru");
        TextTask textTask = new TextTask(1L, "Task 1", lesson);
        Student student = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        String submissionText = "solution";
        SubmissionType submissionType = SubmissionType.STUDENT_SUBMISSION;

        TaskSubmission existingTaskSubmission = new TaskSubmission(existingTaskSubmissionId, textTask, student, curator
                , submissionText, submissionType, LocalDateTime.now());
        Mockito.when(taskSubmissionRepository.findById(existingTaskSubmissionId)).thenReturn(Optional.of(existingTaskSubmission));

        // when
        taskSubmissionService.delete(existingTaskSubmissionId);

        // then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(taskSubmissionRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(existingTaskSubmissionId, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenTaskSubmissionNotFound() {
        //given
        Long nonExistentTaskSubmissionId = 10L;
        Mockito.when(taskSubmissionRepository.findById(nonExistentTaskSubmissionId)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> taskSubmissionService.delete(nonExistentTaskSubmissionId));

        //then
        Assertions.assertEquals("There is no task submission with id = "
                + nonExistentTaskSubmissionId, exception.getMessage());
    }

}

