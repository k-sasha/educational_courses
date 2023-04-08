package com.alex.courses.service;

import com.alex.courses.entity.Administrator;

import java.util.List;

public interface AdminService {
    List<Administrator> getAllAdmins();
    Administrator saveAdmin(Administrator admin);
    Administrator getAdmin(int id);
    void deleteAdmin(int id);
}
