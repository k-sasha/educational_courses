package com.alex.courses.dto.courseDto;

import com.alex.courses.dto.adminDto.AdminResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {

    @Column(name = "name")
    @NotBlank(message = "name is required")
    @Size(min = 2, message = "name must be min 2 symbols")
    private String name;

    @ManyToOne
    @JoinColumn (name = "admin_id")
    private AdminResponseDto adminResponseDto;
}
