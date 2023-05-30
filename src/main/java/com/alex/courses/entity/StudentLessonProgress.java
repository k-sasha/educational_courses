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
@Table(name = "student_lesson_progresses")
@Getter
@Setter
@NoArgsConstructor
public class StudentLessonProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentLessonProgressSeq")
    @SequenceGenerator(name = "studentLessonProgressSeq", sequenceName = "student_lesson_progress_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "curator_id")
    private Curator curator;

    @Column(name = "done")
    private Boolean done;

    public StudentLessonProgress(Long id, Student student, Course course, Lesson lesson, Curator curator, Boolean done) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.lesson = lesson;
        this.curator = curator;
        this.done = done;
    }
}

