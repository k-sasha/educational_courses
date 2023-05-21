package com.alex.courses.service;

import com.alex.courses.dto.courseCuratorDto.CourseCuratorRequestDto;
import com.alex.courses.dto.courseCuratorDto.CourseCuratorResponseDto;
import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.curatorDto.CuratorResponseDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.CourseCurator;
import com.alex.courses.entity.Curator;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.CourseCuratorRepository;
import com.alex.courses.repository.CourseRepository;
import com.alex.courses.repository.CuratorRepository;
import com.alex.courses.service.courseCurator.CourseCuratorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CourseCuratorServiceImplTest {

    @InjectMocks
    private CourseCuratorServiceImpl courseCuratorService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CourseCuratorRepository courseCuratorRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CuratorRepository curatorRepository;

    @Test
    public void shouldReturnAllCoursesCuratorsOrCourseCuratorById() {
        //given
        Course course = new Course(1L, "course1", null);
        Curator curator1 = new Curator(1L, "Anna", "Smirnova", "anna@ya.ru");
        Curator curator2 = new Curator(2L, "Ivan", "Ivanov", "ivan@gmail.com");
        CourseCurator courseCurator1 = new CourseCurator(1L, course, curator1);
        CourseCurator courseCurator2 = new CourseCurator(2L, course, curator2);
        List<CourseCurator> courseCurators = List.of(courseCurator1, courseCurator2);
        Mockito.when(courseCuratorRepository.findAll()).thenReturn(courseCurators);

        Mockito.when(modelMapper.map(courseCurators.get(0), CourseCuratorResponseDto.class))
                .thenReturn(new CourseCuratorResponseDto(1L
                        , new CourseResponseDto(1L,"course1", null )
                        , new CuratorResponseDto(1L, "Anna", "Smirnova")));
        Mockito.when(modelMapper.map(courseCurators.get(1), CourseCuratorResponseDto.class))
                .thenReturn(new CourseCuratorResponseDto(2L
                        , new CourseResponseDto(1L,"course1", null )
                        , new CuratorResponseDto(2L, "Ivan", "Ivanov")));

        CourseCuratorResponseDto responseDto1 = modelMapper.map(courseCurators.get(0), CourseCuratorResponseDto.class);
        CourseCuratorResponseDto responseDto2 = modelMapper.map(courseCurators.get(1), CourseCuratorResponseDto.class);
        List<CourseCuratorResponseDto> expectedResponseDtos = List.of(responseDto1, responseDto2);

        //when
        List<CourseCuratorResponseDto> resultResponseDtos = courseCuratorService.getAllCoursesCurators();

        //then
        Assertions.assertEquals(expectedResponseDtos, resultResponseDtos);
        Assertions.assertEquals(expectedResponseDtos.get(0), resultResponseDtos.get(0));
        Assertions.assertEquals(expectedResponseDtos.get(1), resultResponseDtos.get(1));
        Assertions.assertEquals(expectedResponseDtos.get(1).getCourse(), resultResponseDtos.get(1).getCourse());
        Assertions.assertEquals(expectedResponseDtos.get(1).getCurator(), resultResponseDtos.get(1).getCurator());
    }

    @Test
    public void shouldSaveCourseCurator() {
        // given
        Long courseId = 2L;
        Long curatorId = 1L;

        Course course = new Course(courseId, "course1", null);
        Curator curator = new Curator(curatorId, "Anna", "Smirnova", "anna@ya.ru");

        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        Mockito.when(curatorRepository.findById(curatorId)).thenReturn(Optional.of(curator));

        CourseCurator courseCurator = new CourseCurator(1L, course, curator);
        CourseCuratorResponseDto expectedResponseDto = new CourseCuratorResponseDto(1L
                , new CourseResponseDto(courseId, "course1", null)
                , new CuratorResponseDto(curatorId, "Anna", "Smirnova"));

        Mockito.when(modelMapper.map(any(CourseCurator.class), eq(CourseCuratorResponseDto.class))).thenReturn(expectedResponseDto);
        Mockito.when(courseCuratorRepository.save(any(CourseCurator.class))).thenReturn(courseCurator);

        // when
        CourseCuratorRequestDto requestDto = new CourseCuratorRequestDto(courseId, curatorId);
        CourseCuratorResponseDto resultSavedResponseDto = courseCuratorService.saveCourseCurator(requestDto);

        // then
        Assertions.assertEquals(expectedResponseDto.getId(), resultSavedResponseDto.getId());
        Assertions.assertEquals(expectedResponseDto.getCourse(), resultSavedResponseDto.getCourse());
        Assertions.assertEquals(expectedResponseDto.getCurator(), resultSavedResponseDto.getCurator());
    }

    @Test
    public void shouldDeleteCourseCurator() {
        //given
        Long existingCourseCuratorId = 1L;
        CourseCurator existingCourseCurator = new CourseCurator(existingCourseCuratorId, new Course(), new Curator());
        Mockito.when(courseCuratorRepository.findById(existingCourseCuratorId)).thenReturn(Optional.of(existingCourseCurator));

        //when
        courseCuratorService.deleteCourseCurator(existingCourseCuratorId);

        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(courseCuratorRepository).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(existingCourseCuratorId, argumentCaptor.getValue());
    }

    @Test
    public void shouldThrowResourceNotFoundExceptionWhenCourseCuratorNotFound() {
        //given
        Long nonExistentCourseCuratorId = 10L;
        Mockito.when(courseCuratorRepository.findById(nonExistentCourseCuratorId)).thenReturn(Optional.empty());

        //when
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class
                , () -> courseCuratorService.deleteCourseCurator(nonExistentCourseCuratorId));

        //then
        Assertions.assertEquals("There is no course-curator with id = "
                + nonExistentCourseCuratorId, exception.getMessage());
    }


}
