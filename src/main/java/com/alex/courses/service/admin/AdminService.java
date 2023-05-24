package com.alex.courses.service.admin;


import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.adminDto.AdminRequestDto;
import com.alex.courses.dto.adminDto.AdminResponseDto;
import com.alex.courses.dto.adminDto.AdminUpdateDto;

import java.util.List;

public interface AdminService {
    List<AdminResponseDto> getAll();
    AdminRequestDto save(AdminRequestDto adminDto);
    AdminResponseDto get(Long id);
    void delete(Long id);

    AdminUpdateDto update(Long id, AdminUpdateDto updatedAdminDto);
    CourseResponseDto addCourseToAdmin(Long admin_id, Long course_id);

}
