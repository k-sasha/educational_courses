package com.alex.courses.service;

import com.alex.courses.dto.adminDto.AdminRequestDto;
import com.alex.courses.dto.adminDto.AdminResponseDto;
import com.alex.courses.dto.adminDto.AdminUpdateDto;
import com.alex.courses.entity.Administrator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


@ExtendWith(MockitoExtension.class)
public class ModelMapperAdminTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Test
    public void shouldMapAdminRequestDtoToAdmin() {
        // given
        AdminRequestDto adminRequestDto = new AdminRequestDto("Ivan", "Ivanov", "ivan@gmail.com");

        // when
        Administrator admin = modelMapper.map(adminRequestDto, Administrator.class);

        // then
        Assertions.assertEquals(adminRequestDto.getName(), admin.getName());
        Assertions.assertEquals(adminRequestDto.getSurname(), admin.getSurname());
        Assertions.assertEquals(adminRequestDto.getEmail(), admin.getEmail());
    }

    @Test
    public void shouldMapAdminToAdminResponseDto() {
        // given
        Administrator admin = new Administrator(1L, "John", "Doe", "john.doe@example.com");

        // when
        AdminResponseDto adminResponseDto = modelMapper.map(admin, AdminResponseDto.class);

        // then
        Assertions.assertEquals(admin.getId(), adminResponseDto.getId());
        Assertions.assertEquals(admin.getName(), adminResponseDto.getName());
        Assertions.assertEquals(admin.getSurname(), adminResponseDto.getSurname());
    }

    @Test
    public void shouldMapAdminToAdminUpdateDto() {
        // given
        Administrator admin = new Administrator(1L, "Ivan", "Ivanov", "ivan@gmail.com");

        // when
        AdminUpdateDto adminUpdateDto = modelMapper.map(admin, AdminUpdateDto.class);

        // then
        Assertions.assertEquals(admin.getEmail(), adminUpdateDto.getEmail());
    }

}