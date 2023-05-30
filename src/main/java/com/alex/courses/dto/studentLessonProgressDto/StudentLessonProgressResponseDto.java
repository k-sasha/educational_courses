package com.alex.courses.dto.studentLessonProgressDto;

import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.curatorDto.CuratorResponseDto;
import com.alex.courses.dto.lessonDto.LessonResponseDto;
import com.alex.courses.dto.studentDto.StudentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentLessonProgressResponseDto {
    private Long id;
    private StudentResponseDto student;
    private CourseResponseDto course;
    private LessonResponseDto lesson;
    private CuratorResponseDto curator;
    private Boolean done;
}

