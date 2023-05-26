package com.alex.courses.dto.videoDto;

import com.alex.courses.dto.lessonDto.LessonResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoResponseDto {
    private Long id;
    private String videoUrl;
    private LessonResponseDto lesson;
}
