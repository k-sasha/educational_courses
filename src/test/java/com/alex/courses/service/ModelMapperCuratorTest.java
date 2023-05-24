package com.alex.courses.service;

import com.alex.courses.dto.curatorDto.CuratorRequestDto;
import com.alex.courses.dto.curatorDto.CuratorResponseDto;
import com.alex.courses.dto.curatorDto.CuratorUpdateDto;
import com.alex.courses.entity.Curator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


@ExtendWith(MockitoExtension.class)
public class ModelMapperCuratorTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Test
    public void shouldMapCuratorRequestDtoToCurator() {
        // given
        CuratorRequestDto curatorRequestDto = new CuratorRequestDto("Ivan", "Ivanov", "ivan@gmail.com");

        // when
        Curator curator = modelMapper.map(curatorRequestDto, Curator.class);

        // then
        Assertions.assertEquals(curatorRequestDto.getName(), curator.getName());
        Assertions.assertEquals(curatorRequestDto.getSurname(), curator.getSurname());
        Assertions.assertEquals(curatorRequestDto.getEmail(), curator.getEmail());
    }

    @Test
    public void shouldMapCuratorToCuratorResponseDto() {
        // given
        Curator curator = new Curator(1L, "John", "Doe", "john.doe@example.com");

        // when
        CuratorResponseDto curatorResponseDto = modelMapper.map(curator, CuratorResponseDto.class);

        // then
        Assertions.assertEquals(curator.getId(), curatorResponseDto.getId());
        Assertions.assertEquals(curator.getName(), curatorResponseDto.getName());
        Assertions.assertEquals(curator.getSurname(), curatorResponseDto.getSurname());
    }

    @Test
    public void shouldMapCuratorToCuratorUpdateDto() {
        // given
        Curator curator = new Curator(1L, "Ivan", "Ivanov", "ivan@gmail.com");

        // when
        CuratorUpdateDto curatorUpdateDto = modelMapper.map(curator, CuratorUpdateDto.class);

        // then
        Assertions.assertEquals(curator.getEmail(), curatorUpdateDto.getEmail());
    }

}