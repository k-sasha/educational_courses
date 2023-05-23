package com.alex.courses.controller;

import com.alex.courses.dto.accessDto.AccessRequestDto;
import com.alex.courses.dto.accessDto.AccessResponseDto;
import com.alex.courses.service.access.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accesses")
public class AccessController {
    @Autowired
    private final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @GetMapping
    public ResponseEntity<List<AccessResponseDto>> showAllAccesses() {
        return ResponseEntity.ok(accessService.getAllAccesses());
    }

    @PostMapping
    public ResponseEntity<AccessResponseDto> addAccess(@Valid @RequestBody AccessRequestDto access) {
        AccessResponseDto newAccess = accessService.saveAccess(access);
        return new ResponseEntity<>(newAccess, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessResponseDto> getAccess(@PathVariable Long id) {
        return ResponseEntity.ok(accessService.getAccess(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccess(@PathVariable Long id) {
        accessService.deleteAccess(id);
        return ResponseEntity.ok("Access with id = " + id + " was deleted");
    }

}
