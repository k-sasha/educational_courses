package com.alex.courses.service;

import com.alex.courses.dto.adminDto.AdminResponseDto;
import com.alex.courses.dto.courseDto.CourseRequestDto;
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
public class ModelMapperCourseTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Test
    public void shouldMapCourseRequestDtoToCourse() {
        // given
        AdminResponseDto adminDto = new AdminResponseDto(1L, "Anna", "Smirnova");
        CourseRequestDto courseDto = new CourseRequestDto("course1", adminDto);

        // when
        Course course = modelMapper.map(courseDto, Course.class);

        // then
        Assertions.assertEquals(courseDto.getName(), course.getName());
        Assertions.assertEquals(course.getAdmin().getId(), courseDto.getAdminResponseDto().getId());
        Assertions.assertEquals(course.getAdmin().getName(), courseDto.getAdminResponseDto().getName());
        Assertions.assertEquals(course.getAdmin().getSurname(), courseDto.getAdminResponseDto().getSurname());
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