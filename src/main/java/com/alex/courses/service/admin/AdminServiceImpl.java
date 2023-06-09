package com.alex.courses.service.admin;

import com.alex.courses.dto.adminDto.AdminRequestDto;
import com.alex.courses.dto.adminDto.AdminResponseDto;
import com.alex.courses.dto.adminDto.AdminUpdateDto;
import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.entity.Administrator;
import com.alex.courses.entity.Course;
import com.alex.courses.exseption_handling.ResourceNotFoundException;
import com.alex.courses.repository.AdminRepository;
import com.alex.courses.repository.CourseRepository;
import com.alex.courses.service.admin.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private final AdminRepository adminRepository;

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public AdminServiceImpl(AdminRepository adminRepository
            , CourseRepository courseRepository, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AdminResponseDto> getAll() {
        List<Administrator> admins = adminRepository.findAll();
        return admins.stream().map((admin) -> modelMapper.map(admin, AdminResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdminRequestDto save(AdminRequestDto adminDto) {
        Administrator admin = modelMapper.map(adminDto, Administrator.class);
        Administrator savedAdmin = adminRepository.save(admin);
        AdminRequestDto savedAdminDto = modelMapper.map(savedAdmin, AdminRequestDto.class);
        return savedAdminDto;
    }

    @Override
    public AdminResponseDto get(Long id) {
       Administrator admin = adminRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no admin with id = " + id));
        return modelMapper.map(admin, AdminResponseDto.class);
    }

    @Override
    public void delete(Long id) {
        adminRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no admin with id = " + id));
        adminRepository.deleteById(id);
    }

    @Override
    public AdminUpdateDto update(Long id, AdminUpdateDto updatedAdminDto) {
        Administrator existingAdmin = adminRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("There is no admin with id = " + id));
        existingAdmin.setEmail(updatedAdminDto.getEmail());
        Administrator savedAdmin = adminRepository.save(existingAdmin);
        return modelMapper.map(savedAdmin, AdminUpdateDto.class);
    }

    @Override
    public CourseResponseDto addCourseToAdmin(Long admin_id, Long course_id) {
        Administrator admin = adminRepository.findById(admin_id).orElseThrow(
                () -> new ResourceNotFoundException("There is no admin with id = " + admin_id));
        Course course = courseRepository.findById(course_id).orElseThrow(
                () -> new ResourceNotFoundException("There is no course with id = " + course_id));
        admin.getCourses().add(course);
        course.setAdmin(admin);

        Course savedCourse = courseRepository.save(course);
        Administrator savedAdmin = adminRepository.save(admin);

        return modelMapper.map(savedCourse, CourseResponseDto.class);
    }
}
