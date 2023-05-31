package com.alex.courses.service.textTask;

import com.alex.courses.dto.textTaskDto.TextTaskRequestDto;
import com.alex.courses.dto.textTaskDto.TextTaskResponseDto;
import com.alex.courses.dto.textTaskDto.TextTaskUpdateDto;
import com.alex.courses.entity.Lesson;
import com.alex.courses.entity.TextTask;
import com.alex.courses.exseption_handling.BindingAlreadyExistsException;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.LessonRepository;
import com.alex.courses.repository.TextTaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TextTaskServiceImpl implements TextTaskService {

    private final TextTaskRepository textTaskRepository;
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TextTaskServiceImpl(TextTaskRepository textTaskRepository, LessonRepository lessonRepository, ModelMapper modelMapper) {
        this.textTaskRepository = textTaskRepository;
        this.lessonRepository = lessonRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TextTaskResponseDto> getAll() {
        List<TextTask> textTasks = textTaskRepository.findAll();
        return textTasks.stream()
                .map(textTask -> modelMapper.map(textTask, TextTaskResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TextTaskResponseDto save(TextTaskRequestDto textTaskDto) {
        Long lessonId = textTaskDto.getLessonId();

        Lesson existingLesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new ResourceNotFoundException("There is no lesson with id = " + lessonId));

        Optional<TextTask> existingTextTask = textTaskRepository.findByLessonIdAndTaskDescription(lessonId, textTaskDto.getTaskDescription());

        if (existingTextTask.isPresent()) {
            // if such text task already exists in this lesson
            throw new BindingAlreadyExistsException("This text task already exists in this lesson");
        }

        TextTask textTask = new TextTask();
        textTask.setTaskDescription(textTaskDto.getTaskDescription());
        textTask.setLesson(existingLesson);

        TextTask savedTextTask = textTaskRepository.save(textTask);

        return modelMapper.map(savedTextTask, TextTaskResponseDto.class);
    }

    // update lesson
    @Override
    @Transactional
    public TextTaskResponseDto update(Long id, TextTaskUpdateDto textTaskDto) {
        Long lessonId = textTaskDto.getLessonId();

        Lesson newLesson = lessonRepository.findById(lessonId).orElseThrow(
                () -> new ResourceNotFoundException("There is no lesson with id = " + lessonId));

        TextTask existingTextTask = textTaskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no text task with id = " + id));

        Optional<TextTask> anotherExistingTextTask = textTaskRepository.findByLessonIdAndTaskDescription(lessonId, existingTextTask.getTaskDescription());

        if (anotherExistingTextTask.isPresent()) {
            // if such text task already exists in this lesson
            throw new BindingAlreadyExistsException("This text task already exists in this lesson");
        }

        existingTextTask.setLesson(newLesson);
        TextTask updatedTextTask = textTaskRepository.save(existingTextTask);
        return modelMapper.map(updatedTextTask, TextTaskResponseDto.class);
    }

    @Override
    public TextTaskResponseDto get(Long id) {
        TextTask textTask = textTaskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no text task with id = " + id));
        return modelMapper.map(textTask, TextTaskResponseDto.class);
    }

    @Override
    public void delete(Long id) {
        textTaskRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no text task with id = " + id));
        textTaskRepository.deleteById(id);
    }
}

