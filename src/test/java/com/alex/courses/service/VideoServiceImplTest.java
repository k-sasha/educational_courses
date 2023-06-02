package com.alex.courses.service;

import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.lessonDto.LessonResponseDto;
import com.alex.courses.dto.videoDto.VideoRequestDto;
import com.alex.courses.dto.videoDto.VideoResponseDto;
import com.alex.courses.dto.videoDto.VideoUpdateDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Lesson;
import com.alex.courses.entity.Video;
import com.alex.courses.exseption_handling.BindingAlreadyExistsException;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.LessonRepository;
import com.alex.courses.repository.VideoRepository;
import com.alex.courses.service.video.VideoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class VideoServiceImplTest {

    @InjectMocks
    private VideoServiceImpl videoService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Test
    public void shouldReturnAllVideosOrVideoById() {
        //given
        Course course = new Course(1L, "course1", null);
        Lesson lesson1 = new Lesson(1L, "lesson1", course);
        Video video1 = new Video(1L, "http://video1.com", lesson1);

        Lesson lesson2 = new Lesson(2L, "lesson2", course);
        Video video2 = new Video(2L, "http://video2.com", lesson2);

        List<Video> videos = List.of(video1, video2);

        Mockito.when(videoRepository.findAll()).thenReturn(videos);

        VideoResponseDto responseDto1 = new VideoResponseDto(1L, "http://video1.com"
                , new LessonResponseDto(1L, "lesson1"
                , new CourseResponseDto(1L, "course1", null)));
        Mockito.when(modelMapper.map(videos.get(0), VideoResponseDto.class))
                .thenReturn(responseDto1);

        VideoResponseDto responseDto2 = new VideoResponseDto(2L, "http://video2.com"
                , new LessonResponseDto(2L, "lesson2"
                , new CourseResponseDto(1L, "course1", null)));
        Mockito.when(modelMapper.map(videos.get(1), VideoResponseDto.class))
                .thenReturn(responseDto2);

        List<VideoResponseDto> expectedResponseDtos = List.of(responseDto1, responseDto2);

        //when
        List<VideoResponseDto> resultResponseDtos = videoService.getAll();

        //then
        Assertions.assertEquals(expectedResponseDtos, resultResponseDtos);
        Assertions.assertEquals(expectedResponseDtos.get(0), resultResponseDtos.get(0));
        Assertions.assertEquals(expectedResponseDtos.get(1), resultResponseDtos.get(1));
        Assertions.assertEquals(expectedResponseDtos.get(1).getLesson(), resultResponseDtos.get(1).getLesson());
        Assertions.assertEquals(expectedResponseDtos.get(1).getVideoUrl(), resultResponseDtos.get(1).getVideoUrl());
    }

    @Test
    public void shouldSaveVideo() {
        // given
        Long lessonId = 7L;
        String videoUrl = "http://video.com";

        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);

        Mockito.when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(lesson));
        Mockito.when(videoRepository.findByLessonIdAndVideoUrl(lessonId, videoUrl)).thenReturn(Optional.empty());

        Video video = new Video(1L, videoUrl, lesson);
        VideoResponseDto expectedResponseDto = new VideoResponseDto(1L
                , videoUrl
                , new LessonResponseDto(1L, "lesson1"
                , new CourseResponseDto(1L, "course1", null)));

        Mockito.when(modelMapper.map(any(Video.class), eq(VideoResponseDto.class))).thenReturn(expectedResponseDto);
        Mockito.when(videoRepository.save(any(Video.class))).thenReturn(video);

        // when
        VideoRequestDto requestDto = new VideoRequestDto(videoUrl, lessonId);
        VideoResponseDto resultSavedResponseDto = videoService.save(requestDto);

        // then
        Assertions.assertEquals(expectedResponseDto.getId(), resultSavedResponseDto.getId());
        Assertions.assertEquals(expectedResponseDto.getLesson(), resultSavedResponseDto.getLesson());
        Assertions.assertEquals(expectedResponseDto.getVideoUrl(), resultSavedResponseDto.getVideoUrl());
    }

    @Test
    public void shouldThrowBindingAlreadyExistsExceptionWhenVideoAlreadyExistsInLesson() {
        //given
        Long lessonId = 7L;
        String videoUrl = "http://video.com/existing";

        Course course = new Course(1L, "course1", null);
        Lesson existingLesson = new Lesson(lessonId, "lesson1", course);
        Video existingVideo = new Video(1L, videoUrl, existingLesson);

        Mockito.when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(existingLesson));
        Mockito.when(videoRepository.findByLessonIdAndVideoUrl(lessonId, videoUrl)).thenReturn(Optional.of(existingVideo));

        VideoRequestDto videoDto = new VideoRequestDto();
        videoDto.setLessonId(lessonId);
        videoDto.setVideoUrl(videoUrl);

        //when
        BindingAlreadyExistsException exception = Assertions.assertThrows(BindingAlreadyExistsException.class
                , () -> videoService.save(videoDto));

        //then
        Assertions.assertEquals("This video already exists in this lesson", exception.getMessage());
    }


    @Test
    public void shouldUpdateVideo() {
        //given
        Long videoId = 1L;
        Long updatedLessonId = 2L;
        String existingVideoUrl = "http://video.com/existing";

        Course course = new Course(1L, "course1", null);
        Lesson updatedLesson = new Lesson(updatedLessonId, "lesson2", course);
        Video existingVideo = new Video(videoId, existingVideoUrl, new Lesson(1L, "lesson1", course));

        Mockito.when(videoRepository.findById(videoId)).thenReturn(Optional.of(existingVideo));
        Mockito.when(lessonRepository.findById(updatedLessonId)).thenReturn(Optional.of(updatedLesson));
        Mockito.when(videoRepository.findByLessonIdAndVideoUrl(updatedLessonId, existingVideoUrl)).thenReturn(Optional.empty());

        Video updatedVideo = new Video(videoId, existingVideoUrl, updatedLesson);
        Mockito.when(videoRepository.save(existingVideo)).thenReturn(updatedVideo);

        VideoResponseDto updatedVideoResponseDto = new VideoResponseDto(videoId
                , existingVideoUrl
                , new LessonResponseDto(updatedLessonId, "lesson2"
                , new CourseResponseDto(1L, "course1", null)));

        Mockito.when(modelMapper.map(updatedVideo, VideoResponseDto.class))
                .thenReturn(updatedVideoResponseDto);

        VideoUpdateDto updatedVideoDto = new VideoUpdateDto();
        updatedVideoDto.setLessonId(updatedLessonId);

        //when
        VideoResponseDto resultUpdatedVideoDto = videoService.update(videoId, updatedVideoDto);

        //then
        Assertions.assertEquals(updatedLessonId, resultUpdatedVideoDto.getLesson().getId());
        Mockito.verify(videoRepository).findById(videoId);
        Mockito.verify(lessonRepository).findById(updatedLessonId);
        Mockito.verify(videoRepository).save(existingVideo);
        Mockito.verify(modelMapper).map(updatedVideo, VideoResponseDto.class);
    }

    @Test
    public void shouldDeleteVideo() {
        // given
        Long existingVideoId = 1L;
        Course course = new Course(1L, "course1", null);
        Lesson lesson = new Lesson(1L, "lesson1", course);
        Video existingVideo = new Video(existingVideoId, "http://video.com/existing", lesson);

        Mockito.when(videoRepository.findById(existingVideoId)).thenReturn(Optional.of(existingVideo));

        // when
        videoService.delete(existingVideoId);

        // then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(videoRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(existingVideoId, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenVideoNotFound() {
        //given
        Long nonExistentVideoId = 10L;
        Mockito.when(videoRepository.findById(nonExistentVideoId)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> videoService.delete(nonExistentVideoId));

        //then
        Assertions.assertEquals("There is no video with id = " + nonExistentVideoId, exception.getMessage());
    }

}

