package com.alex.courses.service;

import com.alex.courses.dto.taskSubmissionDto.TaskSubmissionRequestDto;
import com.alex.courses.dto.taskSubmissionDto.TaskSubmissionResponseDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Lesson;
import com.alex.courses.entity.TextTask;
import com.alex.courses.entity.Curator;
import com.alex.courses.entity.Student;
import com.alex.courses.entity.SubmissionType;
import com.alex.courses.entity.TaskSubmission;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.CuratorRepository;
import com.alex.courses.repository.StudentRepository;
import com.alex.courses.repository.TextTaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ModelMapperTaskSubmissionTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Mock
    private TextTaskRepository textTaskRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CuratorRepository curatorRepository;

    @Test
    public void shouldMapTaskSubmissionRequestDtoToTaskSubmission() {
        // given
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);
        TextTask task = new TextTask(1L, "Task 1", lesson);
        Student student = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        Curator curator = new Curator(1L, "Maria", "Petrova", "maria@ya.ru");

        TaskSubmissionRequestDto taskSubmissionDto = new TaskSubmissionRequestDto();
        taskSubmissionDto.setTextTaskId(task.getId());
        taskSubmissionDto.setStudentId(student.getId());
        taskSubmissionDto.setCuratorId(curator.getId());
        taskSubmissionDto.setSubmissionText("solution1");
        taskSubmissionDto.setSubmissionType(SubmissionType.STUDENT_SUBMISSION);

        Mockito.when(textTaskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        Mockito.when(curatorRepository.findById(curator.getId())).thenReturn(Optional.of(curator));

        // when
        TextTask existingTextTask = textTaskRepository.findById(taskSubmissionDto.getTextTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no text task with id = " + taskSubmissionDto.getTextTaskId()));

        Student existingStudent = studentRepository.findById(taskSubmissionDto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no student with id = " + taskSubmissionDto.getStudentId()));

        Curator existingCurator = curatorRepository.findById(taskSubmissionDto.getCuratorId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no curator with id = " + taskSubmissionDto.getCuratorId()));

        TaskSubmission taskSubmission = new TaskSubmission();
        taskSubmission.setTextTask(existingTextTask);
        taskSubmission.setStudent(existingStudent);
        taskSubmission.setCurator(existingCurator);
        taskSubmission.setSubmissionText(taskSubmissionDto.getSubmissionText());
        taskSubmission.setSubmissionType(taskSubmissionDto.getSubmissionType());

        // then
        Assertions.assertEquals(taskSubmissionDto.getTextTaskId(), taskSubmission.getTextTask().getId());
        Assertions.assertEquals(taskSubmissionDto.getStudentId(), taskSubmission.getStudent().getId());
        Assertions.assertEquals(taskSubmissionDto.getCuratorId(), taskSubmission.getCurator().getId());
        Assertions.assertEquals(taskSubmissionDto.getSubmissionText(), taskSubmission.getSubmissionText());
        Assertions.assertEquals(taskSubmissionDto.getSubmissionType(), taskSubmission.getSubmissionType());
    }

    @Test
    public void shouldMapTaskSubmissionToTaskSubmissionResponseDto() {
        //given
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);
        TextTask task = new TextTask(1L, "Task 1", lesson);
        Student student = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        Curator curator = new Curator(1L, "Maria", "Petrova", "maria@ya.ru");
        LocalDateTime localDateTime = LocalDateTime.now();

        TaskSubmission taskSubmission = new TaskSubmission(2L, task, student, curator, "solution1", SubmissionType.STUDENT_SUBMISSION, localDateTime);

        //when
        TaskSubmissionResponseDto taskSubmissionDto = modelMapper.map(taskSubmission, TaskSubmissionResponseDto.class);

        //then
        Assertions.assertEquals(taskSubmission.getId(), taskSubmissionDto.getId());
        Assertions.assertEquals(taskSubmission.getTextTask().getId(), taskSubmissionDto.getTextTaskId());
        Assertions.assertEquals(taskSubmission.getStudent().getId(), taskSubmissionDto.getStudentId());
        Assertions.assertEquals(taskSubmission.getCurator().getId(), taskSubmissionDto.getCuratorId());
        Assertions.assertEquals(taskSubmission.getSubmissionText(), taskSubmissionDto.getSubmissionText());
        Assertions.assertEquals(taskSubmission.getSubmissionType(), taskSubmissionDto.getSubmissionType());
    }
}
