package com.alex.courses.dto.curatorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CuratorUpdateDto {

    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    @NotBlank(message = "email is required")
    @Email(message = "please enter a valid email address")
    private String email;
}
