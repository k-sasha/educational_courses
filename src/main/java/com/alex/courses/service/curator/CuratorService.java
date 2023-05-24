package com.alex.courses.service.curator;

import com.alex.courses.dto.curatorDto.CuratorRequestDto;
import com.alex.courses.dto.curatorDto.CuratorResponseDto;
import com.alex.courses.dto.curatorDto.CuratorUpdateDto;

import java.util.List;

public interface CuratorService {
    List<CuratorResponseDto> getAll();
    CuratorRequestDto save(CuratorRequestDto curatorDto);
    CuratorResponseDto get(Long id);
    void delete(Long id);
    CuratorUpdateDto update(Long id, CuratorUpdateDto updatedCuratorDto);

}
