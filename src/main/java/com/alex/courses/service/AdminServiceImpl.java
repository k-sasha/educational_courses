package com.alex.courses.service;

import com.alex.courses.entity.Administrator;
import com.alex.courses.exseption_handling.NoSuchHumanException;
import com.alex.courses.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public List<Administrator> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Administrator saveAdmin(Administrator admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Administrator getAdmin(long id) {
        return adminRepository.findById(id).orElseThrow(
                () -> new NoSuchHumanException("There is no admin with id = " + id));
    }

    @Override
    public void deleteAdmin(long id) {
        adminRepository.findById(id).orElseThrow(
                () -> new NoSuchHumanException("There is no admin with id = " + id));
        adminRepository.deleteById(id);
    }

    @Override
    public Administrator changeAdmin(long id, Administrator updatedAdmin) {
        Administrator existingAdmin = adminRepository.findById(id).orElseThrow(
                () -> new NoSuchHumanException("There is no admin with id = " + id));
        existingAdmin.setEmail(updatedAdmin.getEmail());
        return adminRepository.save(existingAdmin);
    }
}
