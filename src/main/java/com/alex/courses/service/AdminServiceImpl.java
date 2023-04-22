package com.alex.courses.service;

import com.alex.courses.dto.AdminRequestDto;
import com.alex.courses.dto.AdminResponseDto;
import com.alex.courses.dto.AdminUpdateDto;
import com.alex.courses.entity.Administrator;
import com.alex.courses.entity.Course;
import com.alex.courses.exseption_handling.NoSuchHumanException;
import com.alex.courses.repository.AdminRepository;
import com.alex.courses.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AdminResponseDto> getAllAdmins() {
        List<Administrator> admins = adminRepository.findAll();
        return admins.stream().map((admin) -> modelMapper.map(admin, AdminResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdminRequestDto saveAdmin(AdminRequestDto adminDto) {
        Administrator admin = modelMapper.map(adminDto, Administrator.class);
        Administrator savedAdmin = adminRepository.save(admin);
        AdminRequestDto savedAdminDto = modelMapper.map(savedAdmin, AdminRequestDto.class);
        return savedAdminDto;
    }

    @Override
    public AdminResponseDto getAdmin(Long id) {
       Administrator admin = adminRepository.findById(id).orElseThrow(
                () -> new NoSuchHumanException("There is no admin with id = " + id));
        return modelMapper.map(admin, AdminResponseDto.class);
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository.findById(id).orElseThrow(
                () -> new NoSuchHumanException("There is no admin with id = " + id));
        adminRepository.deleteById(id);
    }

    @Override
    public AdminUpdateDto updateAdmin(Long id, AdminUpdateDto updatedAdminDto) {
        Administrator existingAdmin = adminRepository.findById(id).orElseThrow(
                () -> new NoSuchHumanException("There is no admin with id = " + id));
        existingAdmin.setEmail(updatedAdminDto.getEmail());
        Administrator savedAdmin = adminRepository.save(existingAdmin);
        return modelMapper.map(savedAdmin, AdminUpdateDto.class);
    }

    @Override
    public Course addCourseToAdmin(Long id, Course course) {
        Administrator admin = adminRepository.findById(id).orElseThrow(
                () -> new NoSuchHumanException("There is no admin with id = " + id));
        Course newCourse = courseRepository.save(course);
        admin.getCourses().add(newCourse);
        newCourse.setAdmin(admin);
        return newCourse;
    }
}
