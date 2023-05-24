package com.alex.courses.controller;

import com.alex.courses.dto.lessonDto.LessonRequestDto;
import com.alex.courses.dto.lessonDto.LessonResponseDto;
import com.alex.courses.service.lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    @Autowired
    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<LessonResponseDto>> showAllLessons() {
        return ResponseEntity.ok(lessonService.getAll());
    }

    @PostMapping
    public ResponseEntity<LessonResponseDto> addLesson(@Valid @RequestBody LessonRequestDto lessonDto) {
        LessonResponseDto savedLesson = lessonService.save(lessonDto);
        return new ResponseEntity<>(savedLesson, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<LessonResponseDto> getLesson(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long id) {
        lessonService.delete(id);
        return ResponseEntity.ok("Lesson with id = " + id + " was deleted");
    }
}
