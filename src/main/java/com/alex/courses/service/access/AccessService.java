package com.alex.courses.service.access;

import com.alex.courses.dto.accessDto.AccessRequestDto;
import com.alex.courses.dto.accessDto.AccessResponseDto;

import java.util.List;

public interface AccessService {
    List<AccessResponseDto> getAll();
    AccessResponseDto save(AccessRequestDto accessDto);
    AccessResponseDto get(Long id);
    void delete(Long id);
}
