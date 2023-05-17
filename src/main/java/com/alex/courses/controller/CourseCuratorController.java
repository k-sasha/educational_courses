package com.alex.courses.controller;

import com.alex.courses.dto.courseCuratorDto.CourseCuratorRequestDto;
import com.alex.courses.dto.courseCuratorDto.CourseCuratorResponseDto;
import com.alex.courses.service.courseCurator.CourseCuratorService;
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
@RequestMapping("/course-curators")
public class CourseCuratorController {
    @Autowired
    private final CourseCuratorService courseCuratorService;

    public CourseCuratorController(CourseCuratorService courseCuratorService) {
        this.courseCuratorService = courseCuratorService;
    }

    @GetMapping
    public ResponseEntity<List<CourseCuratorResponseDto>> showAllCoursesCurators() {
        return ResponseEntity.ok(courseCuratorService.getAllCoursesCurators());
    }

    @PostMapping
    public ResponseEntity<CourseCuratorResponseDto> createCourseCurator(@Valid @RequestBody CourseCuratorRequestDto courseCuratorDto) {
        CourseCuratorResponseDto savedCourseCurator = courseCuratorService.saveCourseCurator(courseCuratorDto);
        return new ResponseEntity<>(savedCourseCurator, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CourseCuratorResponseDto> getCourseCurator(@PathVariable Long id) {
        return ResponseEntity.ok(courseCuratorService.getCourseCurator(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourseCurator(@PathVariable Long id) {
        courseCuratorService.deleteCourseCurator(id);
        return ResponseEntity.ok("Course-curator with id = " + id + " was deleted");
    }
}
