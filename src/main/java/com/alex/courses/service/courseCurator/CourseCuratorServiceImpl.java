package com.alex.courses.service.courseCurator;

import com.alex.courses.dto.courseCuratorDto.CourseCuratorRequestDto;
import com.alex.courses.dto.courseCuratorDto.CourseCuratorResponseDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.CourseCurator;
import com.alex.courses.entity.Curator;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.CourseCuratorRepository;
import com.alex.courses.repository.CourseRepository;
import com.alex.courses.repository.CuratorRepository;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseCuratorServiceImpl implements CourseCuratorService {

    @Autowired
    private final CourseCuratorRepository courseCuratorRepository;

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final CuratorRepository curatorRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public CourseCuratorServiceImpl(CourseCuratorRepository courseCuratorRepository
            , CourseRepository courseRepository, CuratorRepository curatorRepository, ModelMapper modelMapper) {
        this.courseCuratorRepository = courseCuratorRepository;
        this.courseRepository = courseRepository;
        this.curatorRepository = curatorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CourseCuratorResponseDto> getAllCoursesCurators() {
        List<CourseCurator> courseCurators = courseCuratorRepository.findAll();
        return courseCurators.stream().map((courseCurator)
                        -> modelMapper.map(courseCurator, CourseCuratorResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CourseCuratorResponseDto saveCourseCurator(CourseCuratorRequestDto courseCuratorDto) {
        Long courseId = Long.valueOf(courseCuratorDto.getCourseId());
        Long curatorId = Long.valueOf(courseCuratorDto.getCuratorId());

        Course existingCourse = courseRepository.findById(courseId).orElseThrow(
                () -> new ResourceNotFoundException("There is no course with id = " + courseId));

        Curator existingCurator = curatorRepository.findById(curatorId).orElseThrow(
                () -> new ResourceNotFoundException("There is no curator with id = " + curatorId));

        CourseCurator courseCurator = new CourseCurator();
        courseCurator.setCourse(existingCourse);
        courseCurator.setCurator(existingCurator);

        CourseCurator savedCourseCurator = courseCuratorRepository.save(courseCurator);
        return modelMapper.map(savedCourseCurator, CourseCuratorResponseDto.class);
    }

    @Override
    public CourseCuratorResponseDto getCourseCurator(Long id) {
        CourseCurator courseCurator = courseCuratorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no course-curator with id = " + id));
        return modelMapper.map(courseCurator, CourseCuratorResponseDto.class);
    }

    @Override
    public void deleteCourseCurator(Long id) {
        CourseCurator courseCurator = courseCuratorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no course-curator with id = " + id));
        courseCurator.setCourse(null);
        courseCurator.setCurator(null);
        CourseCurator emptyCourseCurator = courseCuratorRepository.save(courseCurator);
        courseCuratorRepository.deleteById(emptyCourseCurator.getId());
    }
}
