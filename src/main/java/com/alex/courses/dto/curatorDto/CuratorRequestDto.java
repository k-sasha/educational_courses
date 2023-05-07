package com.alex.courses.dto.curatorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CuratorRequestDto {

    @Column(name = "name")
    @NotBlank(message = "name is required")
    @Size(min = 2, message = "name must be min 2 symbols")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "surname is required")
    @Size(min = 2, message = "surname must be min 2 symbols")
    private String surname;

    @Column(name = "email")
    @NotBlank(message = "email is required")
    @Email(message = "please enter a valid email address")
    private String email;
}
