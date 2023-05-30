package com.alex.courses.controller;

import com.alex.courses.dto.videoDto.VideoRequestDto;
import com.alex.courses.dto.videoDto.VideoResponseDto;
import com.alex.courses.dto.videoDto.VideoUpdateDto;
import com.alex.courses.service.video.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping
    public ResponseEntity<List<VideoResponseDto>> getAllVideos() {
        return ResponseEntity.ok(videoService.getAll());
    }

    @PostMapping
    public ResponseEntity<VideoResponseDto> addVideo(@Valid @RequestBody VideoRequestDto videoDto) {
        VideoResponseDto createdVideo = videoService.save(videoDto);
        return new ResponseEntity<>(createdVideo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponseDto> updateVideo(@PathVariable Long id,
                                                        @Valid @RequestBody VideoUpdateDto videoDto) {
        return ResponseEntity.ok(videoService.update(id, videoDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponseDto> getVideo(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVideo(@PathVariable Long id) {
        videoService.delete(id);
        return ResponseEntity.ok("Video with id = " + id + " was deleted");
    }
}

