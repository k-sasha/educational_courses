package com.alex.courses.service;


import com.alex.courses.entity.Course;
import com.alex.courses.dto.AdminRequestDto;
import com.alex.courses.dto.AdminResponseDto;
import com.alex.courses.dto.AdminUpdateDto;

import java.util.List;

public interface AdminService {
    List<AdminResponseDto> getAllAdmins();
    AdminRequestDto saveAdmin(AdminRequestDto adminDto);
    AdminResponseDto getAdmin(Long id);
    void deleteAdmin(Long id);

    AdminUpdateDto updateAdmin(Long id, AdminUpdateDto updatedAdminDto);
    Course addCourseToAdmin(Long id, Course course);

}
