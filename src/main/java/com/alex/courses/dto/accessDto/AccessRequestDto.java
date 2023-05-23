package com.alex.courses.dto.accessDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccessRequestDto {

    @NotBlank(message = "plan name is required")
    @Size(min = 2, max = 100, message = "plan name must be between 2 and 100 characters")
    private String planName;


    @NotNull(message = "available lessons count is required")
    @Digits(integer = 10, fraction = 0, message = "available lessons count must be a number without fraction part")
    @Min(value = 0, message = "available lessons count must be greater or equal to 0")
    private Integer availableLessonsCount;
}
