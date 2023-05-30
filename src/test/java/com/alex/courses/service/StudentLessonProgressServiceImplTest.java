package com.alex.courses.service;

import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.curatorDto.CuratorResponseDto;
import com.alex.courses.dto.lessonDto.LessonResponseDto;
import com.alex.courses.dto.studentDto.StudentResponseDto;
import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressRequestDto;
import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressResponseDto;
import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressUpdateDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Lesson;
import com.alex.courses.entity.Student;
import com.alex.courses.entity.Curator;
import com.alex.courses.entity.StudentLessonProgress;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.LessonRepository;
import com.alex.courses.repository.StudentLessonProgressRepository;
import com.alex.courses.repository.StudentRepository;
import com.alex.courses.repository.CourseRepository;
import com.alex.courses.repository.CuratorRepository;
import com.alex.courses.service.studentLessonProgress.StudentLessonProgressServiceImpl;
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
public class StudentLessonProgressServiceImplTest {

    @InjectMocks
    private StudentLessonProgressServiceImpl studentLessonProgressService;

    @Mock
    private StudentLessonProgressRepository studentLessonProgressRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CuratorRepository curatorRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void shouldReturnAllStudentLessonProgressesOrStudentLessonProgressesById() {
        //given
        Student student1 = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        Student student2 = new Student(2L, "Ivan", "Ivanov", "ivan@gmail.com");
        Course course1 = new Course(1L, "course1", null);
        Course course2 = new Course(2L, "course2", null);
        Lesson lesson1 = new Lesson(1L, "lesson1", course1);
        Lesson lesson2 = new Lesson(2L, "lesson2", course2);
        Curator curator1 = new Curator(1L, "Maria", "Petrova", "maria@ya.ru");
        Curator curator2 = new Curator(2L, "Petr", "Marov", "petr@gmail.com");

        StudentLessonProgress studentLessonProgress1 = new StudentLessonProgress(1L, student1, course1, lesson1, curator1, true);
        StudentLessonProgress studentLessonProgress2 = new StudentLessonProgress(2L, student2, course2, lesson2, curator2, true);

        List<StudentLessonProgress> studentLessonProgresses = List.of(studentLessonProgress1, studentLessonProgress2);
        Mockito.when(studentLessonProgressRepository.findAll()).thenReturn(studentLessonProgresses);

        Mockito.when(modelMapper.map(studentLessonProgresses.get(0), StudentLessonProgressResponseDto.class))
                .thenReturn(new StudentLessonProgressResponseDto(1L
                        , new StudentResponseDto(1L, "Anna", "Smirnova")
                        , new CourseResponseDto(1L, "course1", null)
                        , new LessonResponseDto(1L, "lesson1", new CourseResponseDto(1L, "course1", null))
                        , new CuratorResponseDto(1L, "Maria", "Petrova"), true));
        Mockito.when(modelMapper.map(studentLessonProgresses.get(1), StudentLessonProgressResponseDto.class))
                .thenReturn(new StudentLessonProgressResponseDto(2L
                        , new StudentResponseDto(2L, "Ivan", "Ivanov")
                        , new CourseResponseDto(2L, "course2", null)
                        , new LessonResponseDto(2L, "lesson2", new CourseResponseDto(2L, "course2", null))
                        , new CuratorResponseDto(2L, "Petr", "Marov"), true));

        StudentLessonProgressResponseDto responseDto1 = modelMapper.map(studentLessonProgresses.get(0), StudentLessonProgressResponseDto.class);
        StudentLessonProgressResponseDto responseDto2 = modelMapper.map(studentLessonProgresses.get(1), StudentLessonProgressResponseDto.class);
        List<StudentLessonProgressResponseDto> expectedResponseDtos = List.of(responseDto1, responseDto2);

        //when
        List<StudentLessonProgressResponseDto> resultResponseDtos = studentLessonProgressService.getAll();

        //then
        Assertions.assertEquals(expectedResponseDtos, resultResponseDtos);
        Assertions.assertEquals(expectedResponseDtos.get(0), resultResponseDtos.get(0));
        Assertions.assertEquals(expectedResponseDtos.get(1), resultResponseDtos.get(1));
        Assertions.assertEquals(expectedResponseDtos.get(1).getStudent(), resultResponseDtos.get(1).getStudent());
        Assertions.assertEquals(expectedResponseDtos.get(1).getCourse(), resultResponseDtos.get(1).getCourse());
        Assertions.assertEquals(expectedResponseDtos.get(1).getLesson(), resultResponseDtos.get(1).getLesson());
        Assertions.assertEquals(expectedResponseDtos.get(1).getCurator(), resultResponseDtos.get(1).getCurator());
    }

    @Test
    public void shouldSaveStudentLessonProgress() {
        // given
        Long studentId = 1L;
        Long courseId = 1L;
        Long lessonId = 1L;
        Long curatorId = 1L;
        boolean doneStatus = true;

        Student student = new Student(studentId, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(courseId, "course1", null);
        Lesson lesson = new Lesson(lessonId, "lesson1", course);
        Curator curator = new Curator(curatorId, "Maria", "Petrova", "maria@ya.ru");

        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        Mockito.when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));
        Mockito.when(curatorRepository.findById(curatorId)).thenReturn(Optional.of(curator));

        StudentLessonProgress studentLessonProgress = new StudentLessonProgress(1L, student, course, lesson, curator, doneStatus);
        StudentLessonProgressResponseDto expectedResponseDto = new StudentLessonProgressResponseDto(1L
                , new StudentResponseDto(1L, "Anna", "Smirnova")
                , new CourseResponseDto(1L, "course1", null)
                , new LessonResponseDto(1L, "lesson1"
                , new CourseResponseDto(courseId, "course1", null))
                , new CuratorResponseDto(1L, "Maria", "Petrova")
                , true);

        Mockito.when(modelMapper.map(any(StudentLessonProgress.class), eq(StudentLessonProgressResponseDto.class))).thenReturn(expectedResponseDto);
        Mockito.when(studentLessonProgressRepository.save(any(StudentLessonProgress.class))).thenReturn(studentLessonProgress);

        // when
        StudentLessonProgressRequestDto requestDto = new StudentLessonProgressRequestDto(studentId, courseId, lessonId, curatorId, doneStatus);
        StudentLessonProgressResponseDto resultSavedResponseDto = studentLessonProgressService.save(requestDto);

        // then
        Assertions.assertEquals(expectedResponseDto.getId(), resultSavedResponseDto.getId());
        Assertions.assertEquals(expectedResponseDto.getStudent(), resultSavedResponseDto.getStudent());
        Assertions.assertEquals(expectedResponseDto.getCourse(), resultSavedResponseDto.getCourse());
        Assertions.assertEquals(expectedResponseDto.getLesson(), resultSavedResponseDto.getLesson());
        Assertions.assertEquals(expectedResponseDto.getCurator(), resultSavedResponseDto.getCurator());
        Assertions.assertEquals(expectedResponseDto.getDone(), resultSavedResponseDto.getDone());
    }

    @Test
    public void shouldUpdateStudentLessonProgress() {
        // given
        Long studentLessonProgressId = 1L;
        Long updatedCuratorId = 2L;
        Boolean updatedDoneStatus = true;

        Student student = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);
        Curator curator = new Curator(1L, "Maria", "Petrova", "maria@ya.ru");
        Curator updatedCurator = new Curator(updatedCuratorId, "Petr", "Marov", "petr@gmail.com");

        StudentLessonProgress existingStudentLessonProgress = new StudentLessonProgress(studentLessonProgressId, student, course, lesson, curator, false);
        Optional<StudentLessonProgress> optionalStudentLessonProgress = Optional.of(existingStudentLessonProgress);
        Mockito.when(studentLessonProgressRepository.findById(studentLessonProgressId)).thenReturn(optionalStudentLessonProgress);

        Mockito.when(curatorRepository.findById(updatedCuratorId)).thenReturn(Optional.of(updatedCurator));

        StudentLessonProgress updatedStudentLessonProgress = new StudentLessonProgress(studentLessonProgressId, student, course, lesson, updatedCurator, updatedDoneStatus);
        Mockito.when(studentLessonProgressRepository.save(existingStudentLessonProgress)).thenReturn(updatedStudentLessonProgress);

        StudentLessonProgressResponseDto updatedStudentLessonProgressResponseDto = new StudentLessonProgressResponseDto(studentLessonProgressId
                , new StudentResponseDto(1L, "Anna", "Smirnova")
                , new CourseResponseDto(1L, "course1", null)
                , new LessonResponseDto(1L, "lesson1"
                , new CourseResponseDto(1L, "course1", null))
                , new CuratorResponseDto(updatedCuratorId, "Petr", "Marov")
                , updatedDoneStatus);

        Mockito.when(modelMapper.map(updatedStudentLessonProgress, StudentLessonProgressResponseDto.class))
                .thenReturn(updatedStudentLessonProgressResponseDto);

        StudentLessonProgressUpdateDto updatedStudentLessonProgressDto = new StudentLessonProgressUpdateDto();
        updatedStudentLessonProgressDto.setCuratorId(updatedCuratorId);
        updatedStudentLessonProgressDto.setDone(updatedDoneStatus);

        // when
        StudentLessonProgressResponseDto resultUpdatedStudentLessonProgressDto = studentLessonProgressService.update(studentLessonProgressId, updatedStudentLessonProgressDto);

        // then
        Assertions.assertEquals(updatedCuratorId, resultUpdatedStudentLessonProgressDto.getCurator().getId());
        Assertions.assertEquals(updatedDoneStatus, resultUpdatedStudentLessonProgressDto.getDone());
        Mockito.verify(studentLessonProgressRepository).findById(studentLessonProgressId);
        Mockito.verify(curatorRepository).findById(updatedCuratorId);
        Mockito.verify(studentLessonProgressRepository).save(existingStudentLessonProgress);
        Mockito.verify(modelMapper).map(updatedStudentLessonProgress, StudentLessonProgressResponseDto.class);
    }


    @Test
    public void shouldDeleteStudentLessonProgress() {
        // given
        Long existingStudentLessonProgressId = 1L;
        Student student = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);
        Curator curator = new Curator(1L, "Maria", "Petrova", "maria@ya.ru");

        StudentLessonProgress existingStudentLessonProgress = new StudentLessonProgress(existingStudentLessonProgressId, student, course, lesson, curator, false);
        Mockito.when(studentLessonProgressRepository.findById(existingStudentLessonProgressId)).thenReturn(Optional.of(existingStudentLessonProgress));

        // when
        studentLessonProgressService.delete(existingStudentLessonProgressId);

        // then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(studentLessonProgressRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(existingStudentLessonProgressId, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenStudentLessonProgressNotFound() {
        //given
        Long nonExistentStudentLessonProgressId = 10L;
        Mockito.when(studentLessonProgressRepository.findById(nonExistentStudentLessonProgressId)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> studentLessonProgressService.delete(nonExistentStudentLessonProgressId));

        //then
        Assertions.assertEquals("There is no student lesson progress with id = "
                + nonExistentStudentLessonProgressId, exception.getMessage());
    }




}

