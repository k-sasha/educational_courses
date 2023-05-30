package com.alex.courses.service.video;

import com.alex.courses.dto.videoDto.VideoRequestDto;
import com.alex.courses.dto.videoDto.VideoResponseDto;
import com.alex.courses.dto.videoDto.VideoUpdateDto;

import java.util.List;

public interface VideoService {
    List<VideoResponseDto> getAll();
    VideoResponseDto save(VideoRequestDto videoDto);
    VideoResponseDto update(Long id, VideoUpdateDto videoDto);
    VideoResponseDto get(Long id);
    void delete(Long id);

}
