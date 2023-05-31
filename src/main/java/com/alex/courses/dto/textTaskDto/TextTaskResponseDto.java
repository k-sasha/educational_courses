package com.alex.courses.dto.textTaskDto;

import com.alex.courses.dto.lessonDto.LessonResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TextTaskResponseDto {
    private Long id;
    private String taskDescription;
    private LessonResponseDto lesson;
}

