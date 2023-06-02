package com.alex.courses.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "task_submissions")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taskSubmissionSeq")
    @SequenceGenerator(name = "taskSubmissionSeq", sequenceName = "task_submission_seq", allocationSize = 1)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "text_task_id")
    @EqualsAndHashCode.Include
    private TextTask textTask;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "curator_id")
    private Curator curator;

    @Column(name = "submission_text")
    private String submissionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "submission_type")
    private SubmissionType submissionType;

    @Column(name = "timestamp")
    @CreatedDate
    private LocalDateTime timestamp;

    public TaskSubmission(Long id, TextTask textTask, Student student, Curator curator
            , String submissionText, SubmissionType submissionType, LocalDateTime timestamp) {
        this.id = id;
        this.textTask = textTask;
        this.student = student;
        this.curator = curator;
        this.submissionText = submissionText;
        this.submissionType = submissionType;
        this.timestamp = timestamp;
    }

}

