package com.alex.courses.service.lesson;

import com.alex.courses.dto.lessonDto.LessonRequestDto;
import com.alex.courses.dto.lessonDto.LessonResponseDto;
import com.alex.courses.entity.Course;
import com.alex.courses.entity.Lesson;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.CourseRepository;
import com.alex.courses.repository.LessonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private final LessonRepository lessonRepository;

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public LessonServiceImpl(LessonRepository lessonRepository
            , CourseRepository courseRepository, ModelMapper modelMapper) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<LessonResponseDto> getAll() {
        List<Lesson> lessons = lessonRepository.findAll();
        return lessons.stream().map((lesson)
                        -> modelMapper.map(lesson, LessonResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LessonResponseDto save(LessonRequestDto lessonDto) {
        Long courseId = lessonDto.getCourseId();

        Course existingCourse = courseRepository.findById(courseId).orElseThrow(
                () -> new ResourceNotFoundException("There is no course with id = " + courseId));

        Lesson lesson = new Lesson();
        lesson.setCourse(existingCourse);
        lesson.setLessonName(lessonDto.getLessonName());

        Lesson savedLesson = lessonRepository.save(lesson);
        return modelMapper.map(savedLesson, LessonResponseDto.class);
    }

    @Override
    public LessonResponseDto get(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no lesson with id = " + id));
        return modelMapper.map(lesson, LessonResponseDto.class);
    }

    @Override
    public void delete(Long id) {
        lessonRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no lesson with id = " + id));
        lessonRepository.deleteById(id);
    }
}
