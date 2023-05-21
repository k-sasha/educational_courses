package com.alex.courses.service;

import com.alex.courses.dto.accessDto.AccessRequestDto;
import com.alex.courses.dto.accessDto.AccessResponseDto;
import com.alex.courses.entity.Access;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


@ExtendWith(MockitoExtension.class)
public class ModelMapperAccessTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Test
    public void shouldMapAccessRequestDtoToAccess() {
        // given
        AccessRequestDto accessRequestDto = new AccessRequestDto("standard", 5);

        // when
        Access access = modelMapper.map(accessRequestDto, Access.class);

        // then
        Assertions.assertEquals(accessRequestDto.getPlanName(), access.getPlanName());
        Assertions.assertEquals(accessRequestDto.getAvailableLessonsCount(), access.getAvailableLessonsCount());
    }

    @Test
    public void shouldMapAccessToAccessResponseDto() {
        // given
        Access access = new Access(1L, "standard", 5);

        // when
        AccessResponseDto accessResponseDto = modelMapper.map(access, AccessResponseDto.class);

        // then
        Assertions.assertEquals(access.getId(), accessResponseDto.getId());
        Assertions.assertEquals(access.getPlanName(), accessResponseDto.getPlanName());
        Assertions.assertEquals(access.getAvailableLessonsCount(), accessResponseDto.getAvailableLessonsCount());
    }

}