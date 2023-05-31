package com.alex.courses.service.textTask;

import com.alex.courses.dto.textTaskDto.TextTaskRequestDto;
import com.alex.courses.dto.textTaskDto.TextTaskResponseDto;
import com.alex.courses.dto.textTaskDto.TextTaskUpdateDto;

import java.util.List;

public interface TextTaskService {
    List<TextTaskResponseDto> getAll();
    TextTaskResponseDto save(TextTaskRequestDto textTaskDto);
    TextTaskResponseDto update(Long id, TextTaskUpdateDto textTaskDto);
    TextTaskResponseDto get(Long id);
    void delete(Long id);

}
