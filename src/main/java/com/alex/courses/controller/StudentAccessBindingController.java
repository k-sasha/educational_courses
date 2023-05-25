package com.alex.courses.controller;

import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingRequestDto;
import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingResponseDto;
import com.alex.courses.dto.studentAccessBindingDto.StudentAccessBindingUpdateDto;
import com.alex.courses.service.studentAccessBinding.StudentAccessBindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student-access-bindings")
public class StudentAccessBindingController {
    @Autowired
    private final StudentAccessBindingService studentAccessBindingService;

    public StudentAccessBindingController(StudentAccessBindingService studentAccessBindingService) {
        this.studentAccessBindingService = studentAccessBindingService;
    }

    @GetMapping
    public ResponseEntity<List<StudentAccessBindingResponseDto>> showAllStudentAccessBindings() {
        return ResponseEntity.ok(studentAccessBindingService.getAll());
    }

    @PostMapping
    public ResponseEntity<StudentAccessBindingResponseDto> addStudentAccessBinding
            (@Valid @RequestBody StudentAccessBindingRequestDto studentAccessBindingDto) {
        StudentAccessBindingResponseDto savedStudentAccessBinding = studentAccessBindingService.save(studentAccessBindingDto);
        return new ResponseEntity<>(savedStudentAccessBinding, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentAccessBindingResponseDto> updateStudentAccessBinding(@PathVariable Long id,
                                                          @Valid @RequestBody StudentAccessBindingUpdateDto studentAccessBinding) {
        return ResponseEntity.ok(studentAccessBindingService.update(id, studentAccessBinding));
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentAccessBindingResponseDto> getStudentAccessBinding(@PathVariable Long id) {
        return ResponseEntity.ok(studentAccessBindingService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentAccessBinding(@PathVariable Long id) {
        studentAccessBindingService.delete(id);
        return ResponseEntity.ok("Student-access-binding with id = " + id + " was deleted");
    }
}
