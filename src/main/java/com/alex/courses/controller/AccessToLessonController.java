package com.alex.courses.controller;

import com.alex.courses.dto.accessToLessonDto.AccessToLessonRequestDto;
import com.alex.courses.dto.accessToLessonDto.AccessToLessonResponseDto;
import com.alex.courses.service.accessToLesson.AccessToLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/access-to-lessons")
public class AccessToLessonController {
    @Autowired
    private final AccessToLessonService accessToLessonService;

    public AccessToLessonController(AccessToLessonService accessToLessonService) {
        this.accessToLessonService = accessToLessonService;
    }

    @GetMapping
    public ResponseEntity<List<AccessToLessonResponseDto>> showAllAccessToLessons() {
        return ResponseEntity.ok(accessToLessonService.getAll());
    }

    @PostMapping
    public ResponseEntity<AccessToLessonResponseDto> addAccessToLesson(@Valid @RequestBody AccessToLessonRequestDto accessToLessonDto) {
        AccessToLessonResponseDto savedAccessToLesson = accessToLessonService.save(accessToLessonDto);
        return new ResponseEntity<>(savedAccessToLesson, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AccessToLessonResponseDto> getAccessToLesson(@PathVariable Long id) {
        return ResponseEntity.ok(accessToLessonService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccessToLesson(@PathVariable Long id) {
        accessToLessonService.delete(id);
        return ResponseEntity.ok("Access-to-lesson with id = " + id + " was deleted");
    }
}
