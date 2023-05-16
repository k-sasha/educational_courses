package com.alex.courses.service;

import com.alex.courses.dto.courseCuratorDto.CourseCuratorRequestDto;
import com.alex.courses.dto.courseCuratorDto.CourseCuratorResponseDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.CourseCurator;
import com.alex.courses.entity.Curator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class ModelMapperCourseCuratorTest {

    @InjectMocks
    private ModelMapper modelMapper;

    @Test
    public void shouldMapCourseCuratorRequestDtoToCourseCurator() {
        // given
        CourseCuratorRequestDto courseCuratorDto = new CourseCuratorRequestDto();
        courseCuratorDto.setCourseId("1");
        courseCuratorDto.setCuratorId("2");

        // when
        CourseCurator courseCurator = modelMapper.map(courseCuratorDto, CourseCurator.class);

        // then
        Assertions.assertEquals(Long.parseLong(courseCuratorDto.getCourseId()), courseCurator.getCourse().getId());
        Assertions.assertEquals(Long.parseLong(courseCuratorDto.getCuratorId()), courseCurator.getCurator().getId());
    }

    @Test
    public void shouldMapCourseCuratorToCourseCuratorResponseDto() {
        //given
        Curator curator = new Curator(1L, "Anna", "Smirnova", "anna@ya.ru");
        Course course = new Course(2L, "Course1", null);
        CourseCurator courseCurator = new CourseCurator(3L, course, curator);

        //when
        CourseCuratorResponseDto courseCuratorDto = modelMapper.map(courseCurator, CourseCuratorResponseDto.class);

        //then
        Assertions.assertEquals(courseCurator.getId(), courseCuratorDto.getId());
        Assertions.assertEquals(courseCurator.getCourse().getId(), courseCuratorDto.getCourse().getId());
        Assertions.assertEquals(courseCurator.getCourse().getName(), courseCuratorDto.getCourse().getName());
        Assertions.assertEquals(courseCurator.getCurator().getId(), courseCuratorDto.getCurator().getId());
        Assertions.assertEquals(courseCurator.getCurator().getName(), courseCuratorDto.getCurator().getName());
        Assertions.assertEquals(courseCurator.getCurator().getSurname(), courseCuratorDto.getCurator().getSurname());
    }
}

