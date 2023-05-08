package com.alex.courses.dto.adminDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDto {

    @NotBlank(message = "name is required")
    @Size(min = 2, message = "name must be min 2 symbols")
    private String name;

    @NotBlank(message = "surname is required")
    @Size(min = 2, message = "surname must be min 2 symbols")
    private String surname;

    @NotBlank(message = "email is required")
    @Email(message = "please enter a valid email address")
    private String email;
}
