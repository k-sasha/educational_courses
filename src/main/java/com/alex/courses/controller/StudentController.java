package com.alex.courses.controller;

import com.alex.courses.dto.studentDto.StudentRequestDto;
import com.alex.courses.dto.studentDto.StudentResponseDto;
import com.alex.courses.dto.studentDto.StudentUpdateDto;
import com.alex.courses.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> showAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());

    }

    @PostMapping
    public ResponseEntity<StudentResponseDto> addStudent(@Valid @RequestBody StudentRequestDto student) {
        StudentResponseDto newStudent = studentService.saveStudent(student);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentUpdateDto> updateStudent(@PathVariable Long id,
                                                          @Valid @RequestBody StudentUpdateDto student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student with id = " + id + " was deleted");
    }

}
