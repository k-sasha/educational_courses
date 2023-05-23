package com.alex.courses.service.access;

import com.alex.courses.dto.accessDto.AccessRequestDto;
import com.alex.courses.dto.accessDto.AccessResponseDto;

import java.util.List;

public interface AccessService {
    List<AccessResponseDto> getAllAccesses();
    AccessResponseDto saveAccess(AccessRequestDto accessDto);
    AccessResponseDto getAccess(Long id);
    void deleteAccess(Long id);
}
