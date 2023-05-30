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
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "videoSeq")
    @SequenceGenerator(name = "videoSeq", sequenceName = "video_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "video_url")
    private String videoUrl;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    public Video(Long id, String videoUrl, Lesson lesson) {
        this.id = id;
        this.videoUrl = videoUrl;
        this.lesson = lesson;
    }
}
