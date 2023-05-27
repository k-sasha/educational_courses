package com.alex.courses.service;

import com.alex.courses.dto.videoDto.VideoRequestDto;
import com.alex.courses.dto.videoDto.VideoResponseDto;
import com.alex.courses.dto.videoDto.VideoUpdateDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Lesson;
import com.alex.courses.entity.Video;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.LessonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ModelMapperVideoTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Mock
    private LessonRepository lessonRepository;

    @Test
    public void shouldMapVideoRequestDtoToVideo() {
        // given
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);
        String videoUrl = "http://video.com/new";

        VideoRequestDto videoDto = new VideoRequestDto();
        videoDto.setLessonId(1L);
        videoDto.setVideoUrl(videoUrl);

        Mockito.when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));

        // when
        Lesson existingLesson = lessonRepository.findById(videoDto.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no lesson with id = " + videoDto.getLessonId()));

        Video video = new Video();
        video.setLesson(existingLesson);
        video.setVideoUrl(videoDto.getVideoUrl());

        // then
        Assertions.assertEquals(videoDto.getLessonId(), video.getLesson().getId());
        Assertions.assertEquals(videoDto.getVideoUrl(), video.getVideoUrl());
    }

    @Test
    public void shouldMapVideoToVideoResponseDto() {
        //given
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);
        String videoUrl = "http://video.com/new";
        Video video = new Video(2L, videoUrl, lesson);

        //when
        VideoResponseDto videoDto = modelMapper.map(video, VideoResponseDto.class);

        //then
        Assertions.assertEquals(video.getId(), videoDto.getId());
        Assertions.assertEquals(video.getLesson().getId(), videoDto.getLesson().getId());
        Assertions.assertEquals(video.getVideoUrl(), videoDto.getVideoUrl());
    }

    @Test
    public void shouldMapVideoUpdateDtoToVideo() {
        // given
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(3L, "lesson3", course);
        String videoUrl = "http://video.com/existing";
        Video existingVideo = new Video(4L, videoUrl, lesson);

        VideoUpdateDto videoUpdateDto = new VideoUpdateDto();
        videoUpdateDto.setLessonId(3L);

        Mockito.when(lessonRepository.findById(3L)).thenReturn(Optional.of(lesson));

        // when
        Lesson existingLesson = lessonRepository.findById(videoUpdateDto.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no lesson with id = " + videoUpdateDto.getLessonId()));

        existingVideo.setLesson(existingLesson);

        // then
        Assertions.assertEquals(videoUpdateDto.getLessonId(), existingVideo.getLesson().getId());
    }

}
