package com.alex.courses.service.curator;

import com.alex.courses.dto.curatorDto.CuratorRequestDto;
import com.alex.courses.dto.curatorDto.CuratorResponseDto;
import com.alex.courses.dto.curatorDto.CuratorUpdateDto;

import java.util.List;

public interface CuratorService {
    List<CuratorResponseDto> getAllCurators();
    CuratorRequestDto saveCurator(CuratorRequestDto curatorDto);
    CuratorResponseDto getCurator(Long id);
    void deleteCurator(Long id);
    CuratorUpdateDto updateCurator(Long id, CuratorUpdateDto updatedCuratorDto);

}
