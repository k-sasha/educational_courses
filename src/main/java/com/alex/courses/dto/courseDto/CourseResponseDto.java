package com.alex.courses.dto.courseDto;

import com.alex.courses.dto.adminDto.AdminResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {

    private Long id;
    private String name;
    private AdminResponseDto admin;

}

