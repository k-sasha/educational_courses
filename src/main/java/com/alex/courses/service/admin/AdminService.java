package com.alex.courses.service.admin;


import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.adminDto.AdminRequestDto;
import com.alex.courses.dto.adminDto.AdminResponseDto;
import com.alex.courses.dto.adminDto.AdminUpdateDto;

import java.util.List;

public interface AdminService {
    List<AdminResponseDto> getAllAdmins();
    AdminRequestDto saveAdmin(AdminRequestDto adminDto);
    AdminResponseDto getAdmin(Long id);
    void deleteAdmin(Long id);

    AdminUpdateDto updateAdmin(Long id, AdminUpdateDto updatedAdminDto);
    CourseResponseDto addCourseToAdmin(Long admin_id, Long course_id);

}
