package com.alex.courses.service;

import com.alex.courses.dto.AdminRequestDto;
import com.alex.courses.dto.AdminResponseDto;
import com.alex.courses.dto.AdminUpdateDto;
import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.entity.Administrator;
import com.alex.courses.entity.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


@ExtendWith(MockitoExtension.class)
public class ModelMapperTest {

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

    @Test
    public void shouldMapCourseToCourseResponseDto() {
        //given
        Administrator admin = new Administrator(1L, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(1L, "Course1", admin);

        //when
        CourseResponseDto courseDto = modelMapper.map(course, CourseResponseDto.class);

        //then
        Assertions.assertEquals(course.getId(), courseDto.getId());
        Assertions.assertEquals(course.getName(), courseDto.getName());
        Assertions.assertEquals(course.getAdmin().getId(), courseDto.getAdmin().getId());
        Assertions.assertEquals(course.getAdmin().getName(), courseDto.getAdmin().getName());
        Assertions.assertEquals(course.getAdmin().getSurname(), courseDto.getAdmin().getSurname());
    }



}