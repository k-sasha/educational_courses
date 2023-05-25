package com.alex.courses.service.studentAccessBinding;

import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingRequestDto;
import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingResponseDto;
import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingUpdateDto;
import com.alex.courses.entity.Access;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Student;
import com.alex.courses.entity.StudentAccessBinding;
import com.alex.courses.exseption_handling.BindingAlreadyExistsException;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.AccessRepository;
import com.alex.courses.repository.CourseRepository;
import com.alex.courses.repository.StudentAccessBindingRepository;
import com.alex.courses.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentAccessBindingServiceImpl implements StudentAccessBindingService {

    private final StudentAccessBindingRepository studentAccessBindingRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AccessRepository accessRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StudentAccessBindingServiceImpl(StudentAccessBindingRepository studentAccessBindingRepository,
                                           StudentRepository studentRepository,
                                           CourseRepository courseRepository,
                                           AccessRepository accessRepository,
                                           ModelMapper modelMapper) {
        this.studentAccessBindingRepository = studentAccessBindingRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.accessRepository = accessRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<StudentAccessBindingResponseDto> getAll() {
        List<StudentAccessBinding> studentAccessBindings = studentAccessBindingRepository.findAll();
        return studentAccessBindings.stream()
                .map((studentAccessBinding) -> modelMapper.map(studentAccessBinding, StudentAccessBindingResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StudentAccessBindingResponseDto save(StudentAccessBindingRequestDto studentAccessBindingDto) {
        Long studentId = studentAccessBindingDto.getStudentId();
        Long courseId = studentAccessBindingDto.getCourseId();
        Long accessId = studentAccessBindingDto.getAccessId();

        Student existingStudent = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("There is no student with id = " + studentId));

        Course existingCourse = courseRepository.findById(courseId).orElseThrow(
                () -> new ResourceNotFoundException("There is no course with id = " + courseId));

        Access existingAccess = accessRepository.findById(accessId).orElseThrow(
                () -> new ResourceNotFoundException("There is no access with id = " + accessId));

        Optional<StudentAccessBinding> existingStudentAccessBinding =
                studentAccessBindingRepository.findByStudentIdAndCourseId(studentId, courseId);

        if (existingStudentAccessBinding.isPresent()) {
            // if student has already been assigned to course
            throw new BindingAlreadyExistsException("The student is already assigned to the course");
        }

        StudentAccessBinding studentAccessBinding = new StudentAccessBinding();
        studentAccessBinding.setStudent(existingStudent);
        studentAccessBinding.setCourse(existingCourse);
        studentAccessBinding.setAccess(existingAccess);

        StudentAccessBinding savedStudentAccessBinding = studentAccessBindingRepository.save(studentAccessBinding);
        return modelMapper.map(savedStudentAccessBinding, StudentAccessBindingResponseDto.class);
    }

    // update access
    @Override
    public StudentAccessBindingResponseDto update(Long id, StudentAccessBindingUpdateDto studentAccessBindingDto) {
        Long accessId = studentAccessBindingDto.getAccessId();

        StudentAccessBinding existingStudentAccessBinding = studentAccessBindingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no student access binding with id = " + id));

        Access existingAccess = accessRepository.findById(accessId).orElseThrow(
                () -> new ResourceNotFoundException("There is no access with id = " + accessId));

        existingStudentAccessBinding.setAccess(existingAccess);
        StudentAccessBinding updatedStudentAccessBinding = studentAccessBindingRepository.save(existingStudentAccessBinding);

        return modelMapper.map(updatedStudentAccessBinding, StudentAccessBindingResponseDto.class);
    }

    @Override
    public StudentAccessBindingResponseDto get(Long id) {
        StudentAccessBinding studentAccessBinding = studentAccessBindingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no student access binding with id = " + id));
        return modelMapper.map(studentAccessBinding, StudentAccessBindingResponseDto.class);
    }

    @Override
    public void delete(Long id) {
        studentAccessBindingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no student access binding with id = " + id));
        studentAccessBindingRepository.deleteById(id);
    }
}
