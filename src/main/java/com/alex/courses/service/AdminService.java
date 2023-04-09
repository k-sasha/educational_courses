package com.alex.courses.service;

import com.alex.courses.entity.Administrator;

import java.util.List;

public interface AdminService {
    List<Administrator> getAllAdmins();
    Administrator saveAdmin(Administrator admin);
    Administrator getAdmin(long id);
    void deleteAdmin(long id);

    Administrator changeAdmin(long id, Administrator updatedAdmin);
}
