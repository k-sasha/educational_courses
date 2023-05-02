package com.alex.courses.service;

import com.alex.courses.dto.courseDto.CourseRequestDto;
import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.entity.Course;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<CourseResponseDto> getAllCorses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(course -> modelMapper.map(course, CourseResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponseDto saveCourse(CourseRequestDto courseDto) {

        Course course = modelMapper.map(courseDto, Course.class);
        Course savedCourse = courseRepository.save(course);
        return modelMapper.map(savedCourse, CourseResponseDto.class);
    }

    @Override
    public CourseResponseDto getCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no course with id = " + id));
        return modelMapper.map(course, CourseResponseDto.class);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no course with id = " + id));
        courseRepository.deleteById(id);
    }
}
