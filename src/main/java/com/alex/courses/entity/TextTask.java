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
@Table(name = "text_tasks")
@Getter
@Setter
@NoArgsConstructor
public class TextTask {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "text_taskSeq")
    @SequenceGenerator(name = "text_taskSeq", sequenceName = "text_task_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "task_description")
    private String taskDescription;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    public TextTask(Long id, String taskDescription, Lesson lesson) {
        this.id = id;
        this.taskDescription = taskDescription;
        this.lesson = lesson;
    }
}
