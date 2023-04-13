package com.alex.courses.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "administrators")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adminSeq")
    @SequenceGenerator(name = "adminSeq", sequenceName = "admin_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "admin")
    private Set<Course> courses = new HashSet<>();

}
