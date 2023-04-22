package com.alex.courses.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "courses")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courseSeq")
    @SequenceGenerator(name = "courseSeq", sequenceName = "course_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "name is required")
    @Size(min = 2, message = "name must be min 2 symbols")
    private String name;

    @ManyToOne
    @JoinColumn (name = "admin_id")
    private Administrator admin;
}
