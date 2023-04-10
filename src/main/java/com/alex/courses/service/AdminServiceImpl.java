package com.alex.courses.service;

import com.alex.courses.dto.AdminDto;
import com.alex.courses.entity.Administrator;
import com.alex.courses.exseption_handling.NoSuchHumanException;
import com.alex.courses.repository.AdminRepository;
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
    private ModelMapper modelMapper;

    @Override
    public List<AdminDto> getAllAdmins() {
        List<Administrator> admins = adminRepository.findAll();
        return admins.stream().map((admin) -> modelMapper.map(admin, AdminDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdminDto saveAdmin(AdminDto adminDto) {
        Administrator admin = modelMapper.map(adminDto, Administrator.class);
        Administrator savedAdmin = adminRepository.save(admin);
        AdminDto savedAdminDto = modelMapper.map(savedAdmin, AdminDto.class);
        return savedAdminDto;
    }

    @Override
    public AdminDto getAdmin(Long id) {
       Administrator admin = adminRepository.findById(id).orElseThrow(
                () -> new NoSuchHumanException("There is no admin with id = " + id));
        return modelMapper.map(admin, AdminDto.class);
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository.findById(id).orElseThrow(
                () -> new NoSuchHumanException("There is no admin with id = " + id));
        adminRepository.deleteById(id);
    }

    @Override
    public AdminDto updateAdmin(Long id, AdminDto updatedAdminDto) {
        Administrator existingAdmin = adminRepository.findById(id).orElseThrow(
                () -> new NoSuchHumanException("There is no admin with id = " + id));
        existingAdmin.setEmail(updatedAdminDto.getEmail());
        Administrator savedAdmin = adminRepository.save(existingAdmin);
        return modelMapper.map(savedAdmin, AdminDto.class);
    }
}
