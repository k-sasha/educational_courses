package com.alex.courses.service;

import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressRequestDto;
import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressResponseDto;
import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressUpdateDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Lesson;
import com.alex.courses.entity.Student;
import com.alex.courses.entity.Curator;
import com.alex.courses.entity.StudentLessonProgress;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.CourseRepository;
import com.alex.courses.repository.CuratorRepository;
import com.alex.courses.repository.LessonRepository;
import com.alex.courses.repository.StudentRepository;
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
public class ModelMapperStudentLessonProgressTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CuratorRepository curatorRepository;

    @Test
    public void shouldMapStudentLessonProgressRequestDtoToStudentLessonProgress() {
        // given
        Student student = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(2L, "course1", null);
        Lesson lesson = new Lesson(3L, "lesson1", course);
        Curator curator = new Curator(4L, "Maria", "Petrova", "maria@ya.ru");

        StudentLessonProgressRequestDto studentLessonProgressDto = new StudentLessonProgressRequestDto();
        studentLessonProgressDto.setStudentId(1L);
        studentLessonProgressDto.setCourseId(2L);
        studentLessonProgressDto.setLessonId(3L);
        studentLessonProgressDto.setCuratorId(4L);
        studentLessonProgressDto.setDone(false);

        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Mockito.when(courseRepository.findById(2L)).thenReturn(Optional.of(course));
        Mockito.when(lessonRepository.findById(3L)).thenReturn(Optional.of(lesson));
        Mockito.when(curatorRepository.findById(4L)).thenReturn(Optional.of(curator));

        // when
        Student existingStudent = studentRepository.findById(studentLessonProgressDto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no student with id = " + studentLessonProgressDto.getStudentId()));

        Course existingCourse = courseRepository.findById(studentLessonProgressDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no course with id = " + studentLessonProgressDto.getCourseId()));

        Lesson existingLesson = lessonRepository.findById(studentLessonProgressDto.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no lesson with id = " + studentLessonProgressDto.getLessonId()));

        Curator existingCurator = curatorRepository.findById(studentLessonProgressDto.getCuratorId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no curator with id = " + studentLessonProgressDto.getCuratorId()));

        StudentLessonProgress studentLessonProgress = new StudentLessonProgress();
        studentLessonProgress.setStudent(existingStudent);
        studentLessonProgress.setCourse(existingCourse);
        studentLessonProgress.setLesson(existingLesson);
        studentLessonProgress.setCurator(existingCurator);
        studentLessonProgress.setDone(studentLessonProgressDto.getDone());

        // then
        Assertions.assertEquals(studentLessonProgressDto.getStudentId(), studentLessonProgress.getStudent().getId());
        Assertions.assertEquals(studentLessonProgressDto.getCourseId(), studentLessonProgress.getCourse().getId());
        Assertions.assertEquals(studentLessonProgressDto.getLessonId(), studentLessonProgress.getLesson().getId());
        Assertions.assertEquals(studentLessonProgressDto.getCuratorId(), studentLessonProgress.getCurator().getId());
        Assertions.assertEquals(studentLessonProgressDto.getDone(), studentLessonProgress.getDone());
    }

    @Test
    public void shouldMapStudentLessonProgressToStudentLessonProgressResponseDto() {
        //given
        Student student = new Student(1L, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(2L, "course1", null);
        Lesson lesson = new Lesson(3L, "lesson1", course);
        Curator curator = new Curator(4L, "Maria", "Petrova", "maria@ya.ru");
        Boolean done = false;
        StudentLessonProgress studentLessonProgress = new StudentLessonProgress(5L, student, course, lesson, curator, done);

        //when
        StudentLessonProgressResponseDto studentLessonProgressDto = modelMapper.map(studentLessonProgress, StudentLessonProgressResponseDto.class);

        //then
        Assertions.assertEquals(studentLessonProgress.getId(), studentLessonProgressDto.getId());
        Assertions.assertEquals(studentLessonProgress.getStudent().getId(), studentLessonProgressDto.getStudent().getId());
        Assertions.assertEquals(studentLessonProgress.getCourse().getId(), studentLessonProgressDto.getCourse().getId());
        Assertions.assertEquals(studentLessonProgress.getLesson().getId(), studentLessonProgressDto.getLesson().getId());
        Assertions.assertEquals(studentLessonProgress.getCurator().getId(), studentLessonProgressDto.getCurator().getId());
        Assertions.assertEquals(studentLessonProgress.getDone(), studentLessonProgressDto.getDone());
    }

    @Test
    public void shouldMapStudentLessonProgressUpdateDtoToStudentLessonProgress() {
        // given
        Curator curator = new Curator(3L, "Maria", "Petrova", "maria@ya.ru");
        StudentLessonProgress existingStudentLessonProgress = new StudentLessonProgress(4L, new Student(), new Course(), new Lesson(), curator, false);

        StudentLessonProgressUpdateDto studentLessonProgressUpdateDto = new StudentLessonProgressUpdateDto();
        studentLessonProgressUpdateDto.setCuratorId(3L);
        studentLessonProgressUpdateDto.setDone(true);

        Mockito.when(curatorRepository.findById(3L)).thenReturn(Optional.of(curator));

        // when
        Curator existingCurator = curatorRepository.findById(studentLessonProgressUpdateDto.getCuratorId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no curator with id = " + studentLessonProgressUpdateDto.getCuratorId()));

        existingStudentLessonProgress.setCurator(existingCurator);
        existingStudentLessonProgress.setDone(studentLessonProgressUpdateDto.getDone());

        // then
        Assertions.assertEquals(studentLessonProgressUpdateDto.getCuratorId(), existingStudentLessonProgress.getCurator().getId());
        Assertions.assertEquals(studentLessonProgressUpdateDto.getDone(), existingStudentLessonProgress.getDone());
    }

}
