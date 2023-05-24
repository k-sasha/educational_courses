package com.alex.courses.controller;

import com.alex.courses.dto.curatorDto.CuratorRequestDto;
import com.alex.courses.dto.curatorDto.CuratorResponseDto;
import com.alex.courses.dto.curatorDto.CuratorUpdateDto;
import com.alex.courses.service.curator.CuratorService;
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
@RequestMapping("/curators")
public class CuratorController {
    @Autowired
    private final CuratorService curatorService;

    public CuratorController(CuratorService curatorService) {
        this.curatorService = curatorService;
    }

    @GetMapping
    public ResponseEntity<List<CuratorResponseDto>> showAllCurators() {
        return ResponseEntity.ok(curatorService.getAll());

    }

    @PostMapping
    public ResponseEntity<String> addCurator(@Valid @RequestBody CuratorRequestDto curator) {
        CuratorRequestDto newCurator = curatorService.save(curator);
        String name = newCurator.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body("Curator " + name + " was created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuratorUpdateDto> updateCurator(@PathVariable Long id,
                                                        @Valid @RequestBody CuratorUpdateDto curator) {
        return ResponseEntity.ok(curatorService.update(id, curator));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuratorResponseDto> getCurator(@PathVariable Long id) {
        return ResponseEntity.ok(curatorService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCurator(@PathVariable Long id) {
        curatorService.delete(id);
        return ResponseEntity.ok("Curator with id = " + id + " was deleted");
    }

}
