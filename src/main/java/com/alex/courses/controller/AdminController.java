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
        long id = newAdmin.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin with id = " + id + " was created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrator> updateAdmin(@PathVariable long id,
                                                     @Valid @RequestBody Administrator updatedAdmin) {
        Administrator savedAdmin = adminService.changeAdmin(id, updatedAdmin);
        return ResponseEntity.ok(savedAdmin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrator> getAdmin(@PathVariable long id) {
        Administrator admin = adminService.getAdmin(id);
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHuman(@PathVariable long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin with id = " + id + " was deleted");
    }
}
