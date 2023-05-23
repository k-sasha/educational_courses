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

@Entity
@Table(name = "accesses")
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Access {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accessSeq")
    @SequenceGenerator(name = "accessSeq", sequenceName = "access_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "plan_name")
    private String planName;

    @Column(name = "available_lessons_count")
    private Integer availableLessonsCount;
}
