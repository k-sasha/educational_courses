package com.alex.courses.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateDto {

    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    @NotBlank(message = "email is required")
    @Email(message = "please enter a valid email address")
    private String email;
}
