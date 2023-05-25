package com.alex.courses.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "student_access_bindings")
@Getter
@Setter
@NoArgsConstructor
public class StudentAccessBinding {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentAccessBindingSeq")
    @SequenceGenerator(name = "studentAccessBindingSeq", sequenceName = "student-access_binding_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "access_id")
    private Access access;

    public StudentAccessBinding(Long id, Student student, Course course, Access access) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.access = access;
    }
}
