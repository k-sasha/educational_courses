package com.alex.courses.controller;

import com.alex.courses.entity.Course;
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
    public ResponseEntity<List<Course>> showAllCourses() {
        List<Course> allCourses = courseService.getAllCorses();
        return ResponseEntity.ok(allCourses);
    }

    @PostMapping
    public ResponseEntity<String> addCourse(@Valid @RequestBody Course course) {
        Course newCourse = courseService.saveCourse(course);
        Long id = newCourse.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body("Course with id = " + id + " was created");
    }


    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        Course course = courseService.getCourse(id);
        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course with id = " + id + " was deleted");
    }
}
