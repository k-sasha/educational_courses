package com.alex.courses.dto.accessDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccessResponseDto {

    private Long id;
    private String planName;
    private Integer availableLessonsCount;
}
