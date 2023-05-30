package com.alex.courses.repository;

import com.alex.courses.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findByLessonIdAndVideoUrl(Long lessonId, String videoUrl);

}
