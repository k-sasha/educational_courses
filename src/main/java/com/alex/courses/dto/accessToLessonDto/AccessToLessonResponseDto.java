package com.alex.courses.dto.accessToLessonDto;

import com.alex.courses.dto.accessDto.AccessResponseDto;
import com.alex.courses.dto.lessonDto.LessonResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccessToLessonResponseDto {
    private Long id;
    private LessonResponseDto lesson;
    private AccessResponseDto access;
}
