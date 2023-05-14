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
@Table(name = "course_curator")
@Getter @Setter
@NoArgsConstructor
public class CourseCurator {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courseCuratorSeq")
    @SequenceGenerator(name = "courseCuratorSeq", sequenceName = "course_curator_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "curator_id")
    private Curator curator;
}
