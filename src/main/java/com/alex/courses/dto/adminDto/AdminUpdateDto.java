package com.alex.courses.dto.adminDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateDto {

    private Long id;

    @NotBlank(message = "email is required")
    @Email(message = "please enter a valid email address")
    private String email;
}
