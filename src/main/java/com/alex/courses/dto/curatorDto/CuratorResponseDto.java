package com.alex.courses.dto.curatorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CuratorResponseDto {

    private Long id;
    private String name;
    private String surname;
}
