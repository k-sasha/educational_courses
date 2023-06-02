package com.alex.courses.controller;

import com.alex.courses.dto.taskSubmissionDto.TaskSubmissionRequestDto;
import com.alex.courses.dto.taskSubmissionDto.TaskSubmissionResponseDto;
import com.alex.courses.service.taskSubmission.TaskSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/task-submissions")
public class TaskSubmissionController {

    private final TaskSubmissionService taskSubmissionService;

    @Autowired
    public TaskSubmissionController(TaskSubmissionService taskSubmissionService) {
        this.taskSubmissionService = taskSubmissionService;
    }

    @GetMapping
    public ResponseEntity<List<TaskSubmissionResponseDto>> getAllTaskSubmissions() {
        return ResponseEntity.ok(taskSubmissionService.getAll());
    }

    @PostMapping
    public ResponseEntity<TaskSubmissionResponseDto> addTaskSubmission
            (@Valid @RequestBody TaskSubmissionRequestDto taskSubmissionDto) {
        TaskSubmissionResponseDto createdTaskSubmission = taskSubmissionService.save(taskSubmissionDto);
        return new ResponseEntity<>(createdTaskSubmission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskSubmissionResponseDto> getTaskSubmission(@PathVariable Long id) {
        return ResponseEntity.ok(taskSubmissionService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaskSubmission(@PathVariable Long id) {
        taskSubmissionService.delete(id);
        return ResponseEntity.ok("TaskSubmission with id = " + id + " was deleted");
    }
}
