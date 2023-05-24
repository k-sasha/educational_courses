package com.alex.courses.controller;

import com.alex.courses.dto.courseDto.CourseResponseDto;
import com.alex.courses.dto.adminDto.AdminRequestDto;
import com.alex.courses.dto.adminDto.AdminResponseDto;
import com.alex.courses.dto.adminDto.AdminUpdateDto;
import com.alex.courses.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/administrators")
public class AdminController {
    @Autowired
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<AdminResponseDto>> showAllAdmins() {
        return ResponseEntity.ok(adminService.getAll());

    }

    @PostMapping
    public ResponseEntity<String> addAdmin(@Valid @RequestBody AdminRequestDto admin) {
        AdminRequestDto newAdmin = adminService.save(admin);
        String name = newAdmin.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin " + name + " was created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminUpdateDto> updateAdmin(@PathVariable Long id,
                                                      @Valid @RequestBody AdminUpdateDto admin) {
        return ResponseEntity.ok(adminService.update(id, admin));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDto> getAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id) {
        adminService.delete(id);
        return ResponseEntity.ok("Admin with id = " + id + " was deleted");
    }

    @PutMapping("/admin/{admin_id}/addCourse/{course_id}")
    public ResponseEntity<CourseResponseDto> addCourseToAdmin(@PathVariable Long admin_id,
                                                              @PathVariable Long course_id) {
        return ResponseEntity.ok(adminService.addCourseToAdmin(admin_id, course_id));
    }
}
