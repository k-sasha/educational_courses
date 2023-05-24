package com.alex.courses.service.accessToLesson;

import com.alex.courses.dto.accessToLessonDto.AccessToLessonRequestDto;
import com.alex.courses.dto.accessToLessonDto.AccessToLessonResponseDto;
import com.alex.courses.entity.Access;
import com.alex.courses.entity.AccessToLesson;
import com.alex.courses.entity.Lesson;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.AccessRepository;
import com.alex.courses.repository.AccessToLessonRepository;
import com.alex.courses.repository.LessonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccessToLessonServiceImpl implements AccessToLessonService {

    @Autowired
    private final AccessToLessonRepository accessToLessonRepository;

    @Autowired
    private final LessonRepository lessonRepository;

    @Autowired
    private final AccessRepository accessRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public AccessToLessonServiceImpl(AccessToLessonRepository accessToLessonRepository
            , LessonRepository lessonRepository, AccessRepository accessRepository, ModelMapper modelMapper) {
        this.accessToLessonRepository = accessToLessonRepository;
        this.lessonRepository = lessonRepository;
        this.accessRepository = accessRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AccessToLessonResponseDto> getAll() {
        List<AccessToLesson> accessToLessons = accessToLessonRepository.findAll();
        return accessToLessons.stream().map((accessToLesson)
                        -> modelMapper.map(accessToLesson, AccessToLessonResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AccessToLessonResponseDto save(AccessToLessonRequestDto accessToLessonDto) {
        Long lessonId = accessToLessonDto.getLessonId();
        Long accessId = accessToLessonDto.getAccessId();

        Lesson existingLesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new ResourceNotFoundException("There is no lesson with id = " + lessonId));

        Access existingAccess = accessRepository.findById(accessId).orElseThrow(
                () -> new ResourceNotFoundException("There is no access with id = " + accessId));

        Optional<AccessToLesson> existingAccessToLesson = accessToLessonRepository.findByLessonId(lessonId);

        AccessToLesson accessToLesson;
        if(existingAccessToLesson.isPresent()){
            // if lesson has already been accessed, then update it
            accessToLesson = existingAccessToLesson.get();
            accessToLesson.setAccess(existingAccess);
        } else {
            // if lesson has no access, create it
            accessToLesson = new AccessToLesson();
            accessToLesson.setLesson(existingLesson);
            accessToLesson.setAccess(existingAccess);
        }

        AccessToLesson savedAccessToLesson = accessToLessonRepository.save(accessToLesson);
        return modelMapper.map(savedAccessToLesson, AccessToLessonResponseDto.class);
    }

    @Override
    public AccessToLessonResponseDto get(Long id) {
        AccessToLesson accessToLesson = accessToLessonRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no access-to-lesson with id = " + id));
        return modelMapper.map(accessToLesson, AccessToLessonResponseDto.class);
    }

    @Override
    public void delete(Long id) {
        accessToLessonRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no access-to-lesson with id = " + id));
        accessToLessonRepository.deleteById(id);
    }
}
