package com.alex.courses.entity;

import lombok.AllArgsConstructor;
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
@Table(name = "lessons")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessonSeq")
    @SequenceGenerator(name = "lessonSeq", sequenceName = "lesson_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "lesson_name")
    private String lessonName;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
