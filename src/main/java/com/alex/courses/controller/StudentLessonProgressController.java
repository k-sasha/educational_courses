package com.alex.courses.controller;


import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressRequestDto;
import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressResponseDto;
import com.alex.courses.dto.studentLessonProgressDto.StudentLessonProgressUpdateDto;
import com.alex.courses.service.studentLessonProgress.StudentLessonProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student-lesson-progresses")
public class StudentLessonProgressController {
    private final StudentLessonProgressService studentLessonProgressService;

    @Autowired
    public StudentLessonProgressController(StudentLessonProgressService studentLessonProgressService) {
        this.studentLessonProgressService = studentLessonProgressService;
    }

    @GetMapping
    public ResponseEntity<List<StudentLessonProgressResponseDto>> showAllStudentLessonProgresses() {
        return ResponseEntity.ok(studentLessonProgressService.getAll());
    }

    @PostMapping
    public ResponseEntity<StudentLessonProgressResponseDto> addStudentLessonProgress
            (@Valid @RequestBody StudentLessonProgressRequestDto studentLessonProgressDto) {
        StudentLessonProgressResponseDto savedStudentLessonProgress = studentLessonProgressService.save(studentLessonProgressDto);
        return new ResponseEntity<>(savedStudentLessonProgress, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentLessonProgressResponseDto> updateStudentLessonProgress(@PathVariable Long id,
                                                                                        @Valid @RequestBody StudentLessonProgressUpdateDto studentLessonProgress) {
        return ResponseEntity.ok(studentLessonProgressService.update(id, studentLessonProgress));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentLessonProgressResponseDto> getStudentLessonProgress(@PathVariable Long id) {
        return ResponseEntity.ok(studentLessonProgressService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentLessonProgress(@PathVariable Long id) {
        studentLessonProgressService.delete(id);
        return ResponseEntity.ok("Student-lesson-progress with id = " + id + " was deleted");
    }
}
