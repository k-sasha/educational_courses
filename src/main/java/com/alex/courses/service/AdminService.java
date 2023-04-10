package com.alex.courses.service;

import com.alex.courses.dto.AdminDto;

import java.util.List;

public interface AdminService {
    List<AdminDto> getAllAdmins();
    AdminDto saveAdmin(AdminDto adminDto);
    AdminDto getAdmin(Long id);
    void deleteAdmin(Long id);

    AdminDto updateAdmin(Long id, AdminDto updatedAdminDto);
}
