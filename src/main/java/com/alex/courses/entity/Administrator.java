package com.alex.courses.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "administrators")
@Getter @Setter @NoArgsConstructor
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adminSeq")
    @SequenceGenerator(name = "adminSeq", sequenceName = "admin_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

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

    public Administrator(int id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
