package com.alex.courses.controller;

import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.adminDto.AdminRequestDto;
import com.alex.courses.dto.adminDto.AdminResponseDto;
import com.alex.courses.dto.adminDto.AdminUpdateDto;
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
    public ResponseEntity<List<AdminResponseDto>> showAllAdmins() {
        List<AdminResponseDto> allAdmins = adminService.getAllAdmins();
        return ResponseEntity.ok(allAdmins);

    }

    @PostMapping
    public ResponseEntity<String> addAdmin(@Valid @RequestBody AdminRequestDto admin) {
        AdminRequestDto newAdmin = adminService.saveAdmin(admin);
        String name = newAdmin.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin " + name + " was created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminUpdateDto> updateAdmin(@PathVariable Long id,
                                                      @Valid @RequestBody AdminUpdateDto admin) {
        AdminUpdateDto updatedAdmin = adminService.updateAdmin(id, admin);
        return ResponseEntity.ok(updatedAdmin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDto> getAdmin(@PathVariable Long id) {
        AdminResponseDto admin = adminService.getAdmin(id);
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin with id = " + id + " was deleted");
    }

    @PutMapping("/admin/{admin_id}/addCourse/{course_id}")
    public ResponseEntity<CourseResponseDto> addCourseToAdmin(@PathVariable Long admin_id,
                                                              @PathVariable Long course_id) {
        CourseResponseDto newCourse = adminService.addCourseToAdmin(admin_id, course_id);
        return ResponseEntity.ok(newCourse);
    }
}
