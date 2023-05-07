package com.alex.courses.controller;

import com.alex.courses.dto.courseDto.CourseRequestDto;
import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> showAllCourses() {
        List<CourseResponseDto> allCourses = courseService.getAllCourses();
        return ResponseEntity.ok(allCourses);
    }

    @PostMapping
    public ResponseEntity<String> addCourse(@Valid @RequestBody CourseRequestDto courseDto) {
        CourseResponseDto newCourse = courseService.saveCourse(courseDto);
        Long id = newCourse.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body("Course with id = " + id + " was created");
    }


    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourse(@PathVariable Long id) {
        CourseResponseDto courseDto = courseService.getCourse(id);
        return ResponseEntity.ok(courseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course with id = " + id + " was deleted");
    }
}