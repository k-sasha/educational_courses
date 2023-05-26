package com.alex.courses.service.studentLessonProgress;

import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressRequestDto;
import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressResponseDto;
import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressUpdateDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Student;
import com.alex.courses.entity.Curator;
import com.alex.courses.entity.Lesson;
import com.alex.courses.entity.StudentLessonProgress;
import com.alex.courses.entity.StudentAccessBinding;
import com.alex.courses.exseption_handling.AccessNotGrantedException;
import com.alex.courses.exseption_handling.BindingNotFoundException;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.exseption_handling.BindingAlreadyExistsException;
import com.alex.courses.repository.CourseRepository;
import com.alex.courses.repository.StudentLessonProgressRepository;
import com.alex.courses.repository.StudentRepository;
import com.alex.courses.repository.CuratorRepository;
import com.alex.courses.repository.LessonRepository;
import com.alex.courses.repository.StudentAccessBindingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentLessonProgressServiceImpl implements StudentLessonProgressService {

    private final StudentLessonProgressRepository studentLessonProgressRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final CuratorRepository curatorRepository;
    private final StudentAccessBindingRepository studentAccessBindingRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public StudentLessonProgressServiceImpl(StudentLessonProgressRepository studentLessonProgressRepository,
                                            StudentRepository studentRepository,
                                            CourseRepository courseRepository,
                                            LessonRepository lessonRepository,
                                            CuratorRepository curatorRepository,
                                            StudentAccessBindingRepository studentAccessBindingRepository, ModelMapper modelMapper) {
        this.studentLessonProgressRepository = studentLessonProgressRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.curatorRepository = curatorRepository;
        this.studentAccessBindingRepository = studentAccessBindingRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<StudentLessonProgressResponseDto> getAll() {
        List<StudentLessonProgress> studentLessonProgressList = studentLessonProgressRepository.findAll();
        return studentLessonProgressList.stream()
                .map((studentLessonProgress) -> modelMapper.map(studentLessonProgress, StudentLessonProgressResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentLessonProgressResponseDto save(StudentLessonProgressRequestDto studentLessonProgressDto) {
        Long studentId = studentLessonProgressDto.getStudentId();
        Long courseId = studentLessonProgressDto.getCourseId();
        Long lessonId = studentLessonProgressDto.getLessonId();
        Long curatorId = studentLessonProgressDto.getCuratorId();

        // check if the student, course, lesson, and curator exist
        Student existingStudent = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("There is no student with id = " + studentId));
        Course existingCourse = courseRepository.findById(courseId).orElseThrow(
                () -> new ResourceNotFoundException("There is no course with id = " + courseId));
        Lesson existingLesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new ResourceNotFoundException("There is no lesson with id = " + lessonId));
        Curator existingCurator = curatorRepository.findById(curatorId).orElseThrow(
                () -> new ResourceNotFoundException("There is no curator with id = " + curatorId));

        // 1) Check if the student is allowed to take this lesson
        StudentAccessBinding existingStudentAccessBinding =
                studentAccessBindingRepository.findByStudentIdAndCourseId(studentId, courseId)
                        .orElseThrow(() -> new BindingNotFoundException("There is no binding between student with id = " + studentId + " and course with id = " + courseId));

        // Check if the number of lessons taken by the student is less than the available lessons count in the access
        Long lessonsTakenCount = studentLessonProgressRepository.countByStudentIdAndCourseId(studentId, courseId);
        if (lessonsTakenCount >= existingStudentAccessBinding.getAccess().getAvailableLessonsCount()) {
            throw new AccessNotGrantedException("The student with id = " + studentId + " does not have access to the lesson with id = " + lessonId);
        }

//      2) Check if there is already a binding for this student and this lesson
        Optional<StudentLessonProgress> existingStudentLessonBinding =
                studentLessonProgressRepository.findByStudentIdAndLessonId(studentId, lessonId);
        if (existingStudentLessonBinding.isPresent()) {
            throw new BindingAlreadyExistsException("The binding between student with id = " + studentId
                    + " and lesson with id = " + lessonId + " already exists.");
        }

        StudentLessonProgress studentLessonProgress = new StudentLessonProgress();
        studentLessonProgress.setStudent(existingStudent);
        studentLessonProgress.setCourse(existingCourse);
        studentLessonProgress.setLesson(existingLesson);
        studentLessonProgress.setCurator(existingCurator);
        studentLessonProgress.setDone(studentLessonProgressDto.getDone());

        StudentLessonProgress savedStudentLessonProgress = studentLessonProgressRepository.save(studentLessonProgress);
        return modelMapper.map(savedStudentLessonProgress, StudentLessonProgressResponseDto.class);
    }

    @Override
    @Transactional
    public StudentLessonProgressResponseDto update(Long id, StudentLessonProgressUpdateDto studentLessonProgressDto) {
        Long curatorId = studentLessonProgressDto.getCuratorId();
        Boolean done = studentLessonProgressDto.getDone();

        StudentLessonProgress existingStudentLessonProgress = studentLessonProgressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no student lesson progress with id = " + id));

        Curator existingCurator = curatorRepository.findById(curatorId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no curator with id = " + curatorId));

        existingStudentLessonProgress.setCurator(existingCurator);
        existingStudentLessonProgress.setDone(done);

        StudentLessonProgress updatedStudentLessonProgress = studentLessonProgressRepository.save(existingStudentLessonProgress);

        return modelMapper.map(updatedStudentLessonProgress, StudentLessonProgressResponseDto.class);
    }

    @Override
    public StudentLessonProgressResponseDto get(Long id) {
        StudentLessonProgress studentLessonProgress = studentLessonProgressRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no student lesson progress with id = " + id));
        return modelMapper.map(studentLessonProgress, StudentLessonProgressResponseDto.class);
    }

    @Override
    public void delete(Long id) {
        studentLessonProgressRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no student lesson progress with id = " + id));
        studentLessonProgressRepository.deleteById(id);
    }

}

