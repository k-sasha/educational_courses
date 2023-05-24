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
@Table(name = "access_to_lessons")
@Getter @Setter
@NoArgsConstructor
public class AccessToLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AccessToLessonSeq")
    @SequenceGenerator(name = "access_to_lessonSeq", sequenceName = "access_to_lesson_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "access_id")
    private Access access;

    public AccessToLesson(Long id, Lesson lesson, Access access) {
        this.id = id;
        this.lesson = lesson;
        this.access = access;
    }
}
