package com.alex.courses.service.taskSubmission;

import com.alex.courses.dto.taskSubmissionDto.TaskSubmissionRequestDto;
import com.alex.courses.dto.taskSubmissionDto.TaskSubmissionResponseDto;
import com.alex.courses.entity.TaskSubmission;
import com.alex.courses.entity.TextTask;
import com.alex.courses.entity.Curator;
import com.alex.courses.entity.Student;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.StudentRepository;
import com.alex.courses.repository.TaskSubmissionRepository;
import com.alex.courses.repository.TextTaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alex.courses.repository.CuratorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskSubmissionServiceImpl implements TaskSubmissionService {

    private final TaskSubmissionRepository taskSubmissionRepository;
    private final TextTaskRepository textTaskRepository;
    private final StudentRepository studentRepository;
    private final CuratorRepository curatorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskSubmissionServiceImpl(TaskSubmissionRepository taskSubmissionRepository,
                                     TextTaskRepository textTaskRepository,
                                     StudentRepository studentRepository,
                                     CuratorRepository curatorRepository,
                                     ModelMapper modelMapper) {
        this.taskSubmissionRepository = taskSubmissionRepository;
        this.textTaskRepository = textTaskRepository;
        this.studentRepository = studentRepository;
        this.curatorRepository = curatorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TaskSubmissionResponseDto> getAll() {
        List<TaskSubmission> taskSubmissions = taskSubmissionRepository.findAll();
        return taskSubmissions.stream()
                .map(taskSubmission -> modelMapper.map(taskSubmission, TaskSubmissionResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskSubmissionResponseDto save(TaskSubmissionRequestDto taskSubmissionDto) {
        TextTask existingTextTask = textTaskRepository.findById(taskSubmissionDto.getTextTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no text task with id = " + taskSubmissionDto.getTextTaskId()));

        Student existingStudent = studentRepository.findById(taskSubmissionDto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no student with id = " + taskSubmissionDto.getStudentId()));

        Curator existingCurator = curatorRepository.findById(taskSubmissionDto.getCuratorId())
                .orElseThrow(() -> new ResourceNotFoundException("There is no curator with id = " + taskSubmissionDto.getCuratorId()));

        TaskSubmission taskSubmission = new TaskSubmission();
        taskSubmission.setTextTask(existingTextTask);
        taskSubmission.setStudent(existingStudent);
        taskSubmission.setCurator(existingCurator);
        taskSubmission.setSubmissionText(taskSubmissionDto.getSubmissionText());
        taskSubmission.setSubmissionType(taskSubmissionDto.getSubmissionType());

        TaskSubmission savedTaskSubmission = taskSubmissionRepository.save(taskSubmission);

        return modelMapper.map(savedTaskSubmission, TaskSubmissionResponseDto.class);
    }

    @Override
    public TaskSubmissionResponseDto get(Long id) {
        TaskSubmission taskSubmission = taskSubmissionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no task submission with id = " + id));
        return modelMapper.map(taskSubmission, TaskSubmissionResponseDto.class);
    }

    @Override
    public void delete(Long id) {
        taskSubmissionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no task submission with id = " + id));
        taskSubmissionRepository.deleteById(id);
    }
}
