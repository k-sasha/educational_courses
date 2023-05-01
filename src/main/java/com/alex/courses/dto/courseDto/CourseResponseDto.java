package com.alex.courses.dto.courseDto;

import com.alex.courses.dto.AdminResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {

    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "admin")
    private AdminResponseDto admin;

}

