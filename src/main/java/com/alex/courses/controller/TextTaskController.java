package com.alex.courses.controller;

import com.alex.courses.dto.textTaskDto.TextTaskRequestDto;
import com.alex.courses.dto.textTaskDto.TextTaskResponseDto;
import com.alex.courses.dto.textTaskDto.TextTaskUpdateDto;
import com.alex.courses.service.textTask.TextTaskService;
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
@RequestMapping("/textTasks")
public class TextTaskController {

    private final TextTaskService textTaskService;

    @Autowired
    public TextTaskController(TextTaskService textTaskService) {
        this.textTaskService = textTaskService;
    }

    @GetMapping
    public ResponseEntity<List<TextTaskResponseDto>> getAllTextTasks() {
        return ResponseEntity.ok(textTaskService.getAll());
    }

    @PostMapping
    public ResponseEntity<TextTaskResponseDto> addTextTask(@Valid @RequestBody TextTaskRequestDto textTaskDto) {
        TextTaskResponseDto createdTextTask = textTaskService.save(textTaskDto);
        return new ResponseEntity<>(createdTextTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TextTaskResponseDto> updateTextTask(@PathVariable Long id,
                                                              @Valid @RequestBody TextTaskUpdateDto textTaskDto) {
        return ResponseEntity.ok(textTaskService.update(id, textTaskDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TextTaskResponseDto> getTextTask(@PathVariable Long id) {
        return ResponseEntity.ok(textTaskService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTextTask(@PathVariable Long id) {
        textTaskService.delete(id);
        return ResponseEntity.ok("TextTask with id = " + id + " was deleted");
    }
}
