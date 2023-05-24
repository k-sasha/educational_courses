package com.alex.courses.dto.adminDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDto {

    private Long id;
    private String name;
    private String surname;
}
