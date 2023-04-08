package com.alex.courses.controller;

import com.alex.courses.entity.Administrator;
import com.alex.courses.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/administrators")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<Administrator>> showAllAdmins() {
        List<Administrator> allAdmins = adminService.getAllAdmins();
        return ResponseEntity.ok(allAdmins);

    }

    @PostMapping
    public ResponseEntity<String> addAdmin(@Valid @RequestBody Administrator admin) {
        Administrator newAdmin = adminService.saveAdmin(admin);
        int id = newAdmin.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin with id = " + id + " was created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrator> updateAdmin(@PathVariable int id, @Valid @RequestBody Administrator updatedAdmin) {
        Administrator existingAdmin = adminService.getAdmin(id);

        existingAdmin.setName(updatedAdmin.getName());
        existingAdmin.setSurname(updatedAdmin.getSurname());

        Administrator savedAdmin = adminService.saveAdmin(existingAdmin);
        return ResponseEntity.ok(savedAdmin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrator> getAdmin(@PathVariable int id) {
        Administrator admin = adminService.getAdmin(id);
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHuman(@PathVariable int id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin with id = " + id + " was deleted");
    }
}
