package com.alex.courses.controller;

import com.alex.courses.dto.AdminDto;
import com.alex.courses.entity.Course;
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
    public ResponseEntity<List<AdminDto>> showAllAdmins() {
        List<AdminDto> allAdmins = adminService.getAllAdmins();
        return ResponseEntity.ok(allAdmins);

    }

    @PostMapping
    public ResponseEntity<String> addAdmin(@Valid @RequestBody AdminDto admin) {
        AdminDto newAdmin = adminService.saveAdmin(admin);
        Long id = newAdmin.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin with id = " + id + " was created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminDto> updateAdmin(@PathVariable Long id,
                                                     @Valid @RequestBody AdminDto admin) {
        AdminDto updatedAdmin = adminService.updateAdmin(id, admin);
        return ResponseEntity.ok(updatedAdmin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdmin(@PathVariable Long id) {
        AdminDto admin = adminService.getAdmin(id);
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHuman(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin with id = " + id + " was deleted");
    }

    @PutMapping("/admin/{admin_id}/addCourse")
    public ResponseEntity<Course> addCourseToAdmin(@PathVariable Long admin_id,
                                                     @Valid @RequestBody Course course) {
        Course newCourse = adminService.addCourseToAdmin(admin_id, course);
        return ResponseEntity.ok(newCourse);
    }
}
