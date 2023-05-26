package com.alex.courses.service.video;

import com.alex.courses.dto.videoDto.VideoRequestDto;
import com.alex.courses.dto.videoDto.VideoResponseDto;
import com.alex.courses.dto.videoDto.VideoUpdateDto;
import com.alex.courses.entity.Lesson;
import com.alex.courses.entity.Video;
import com.alex.courses.exseption_handling.BindingAlreadyExistsException;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.LessonRepository;
import com.alex.courses.repository.VideoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {


    private final VideoRepository videoRepository;
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository, LessonRepository lessonRepository, ModelMapper modelMapper) {
        this.videoRepository = videoRepository;
        this.lessonRepository = lessonRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<VideoResponseDto> getAll() {
        List<Video> videos = videoRepository.findAll();
        return videos.stream()
                .map(video -> modelMapper.map(video, VideoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VideoResponseDto save(VideoRequestDto videoDto) {
        Long lessonId = videoDto.getLessonId();

        Lesson existingLesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new ResourceNotFoundException("There is no lesson with id = " + lessonId));

        Optional<Video> existingVideo = videoRepository.findByLessonIdAndVideoUrl(lessonId, videoDto.getVideoUrl());

        if (existingVideo.isPresent()) {
            // if such video already exists in this lesson
            throw new BindingAlreadyExistsException("This video already exists in this lesson");
        }

        Video video = new Video();
        video.setVideoUrl(videoDto.getVideoUrl());
        video.setLesson(existingLesson);

        Video savedVideo = videoRepository.save(video);

        return modelMapper.map(savedVideo, VideoResponseDto.class);
    }

    // update lesson
    @Override
    @Transactional
    public VideoResponseDto update(Long id, VideoUpdateDto videoDto) {
        Long lessonId = videoDto.getLessonId();

        Lesson newLesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new ResourceNotFoundException("There is no lesson with id = " + lessonId));

        Video existingVideo = videoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no video with id = " + id));

        Optional<Video> anotherExistingVideo = videoRepository.findByLessonIdAndVideoUrl(lessonId, existingVideo.getVideoUrl());

        if (anotherExistingVideo.isPresent()) {
            // if such video already exists in this lesson
            throw new BindingAlreadyExistsException("This video already exists in this lesson");
        }

        existingVideo.setLesson(newLesson);
        Video updatedVideo = videoRepository.save(existingVideo);
        return modelMapper.map(updatedVideo, VideoResponseDto.class);
    }

    @Override
    public VideoResponseDto get(Long id) {
        Video video = videoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no video with id = " + id));
        return modelMapper.map(video, VideoResponseDto.class);
    }

    @Override
    public void delete(Long id) {
        videoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no video with id = " + id));
        videoRepository.deleteById(id);
    }

}
