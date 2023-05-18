package com.alex.courses.service.student;

import com.alex.courses.dto.studentDto.StudentRequestDto;
import com.alex.courses.dto.studentDto.StudentResponseDto;
import com.alex.courses.dto.studentDto.StudentUpdateDto;
import com.alex.courses.entity.Student;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<StudentResponseDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map((student) -> modelMapper.map(student, StudentResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDto saveStudent(StudentRequestDto studentDto) {
        Student student = modelMapper.map(studentDto, Student.class);
        Student savedStudent = studentRepository.save(student);
        return modelMapper.map(savedStudent, StudentResponseDto.class);
    }

    @Override
    public StudentResponseDto getStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no student with id = " + id));
        return modelMapper.map(student, StudentResponseDto.class);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no student with id = " + id));
        studentRepository.deleteById(id);
    }

    @Override
    public StudentUpdateDto updateStudent(Long id, StudentUpdateDto updatedStudentDto) {
        Student existingStudent = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no student with id = " + id));
        existingStudent.setEmail(updatedStudentDto.getEmail());
        Student savedStudent = studentRepository.save(existingStudent);
        return modelMapper.map(savedStudent, StudentUpdateDto.class);
    }
}
