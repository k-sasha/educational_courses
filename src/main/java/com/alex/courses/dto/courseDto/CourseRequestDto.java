package com.alex.courses.dto.courseDto;

import com.alex.courses.dto.adminDto.AdminResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {

    @NotBlank(message = "name is required")
    @Size(min = 2, message = "name must be min 2 symbols")
    private String name;

    private AdminResponseDto adminResponseDto;
}
