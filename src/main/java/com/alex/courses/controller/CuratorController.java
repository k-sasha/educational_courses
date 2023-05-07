package com.alex.courses.controller;

import com.alex.courses.dto.curatorDto.CuratorRequestDto;
import com.alex.courses.dto.curatorDto.CuratorResponseDto;
import com.alex.courses.dto.curatorDto.CuratorUpdateDto;
import com.alex.courses.service.CuratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/curators")
public class CuratorController {
    @Autowired
    private CuratorService curatorService;

    @GetMapping
    public ResponseEntity<List<CuratorResponseDto>> showAllCurators() {
        List<CuratorResponseDto> allCurators = curatorService.getAllCurators();
        return ResponseEntity.ok(allCurators);

    }

    @PostMapping
    public ResponseEntity<String> addCurator(@Valid @RequestBody CuratorRequestDto curator) {
        CuratorRequestDto newCurator = curatorService.saveCurator(curator);
        String name = newCurator.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body("Curator " + name + " was created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuratorUpdateDto> updateCurator(@PathVariable Long id,
                                                        @Valid @RequestBody CuratorUpdateDto curator) {
        CuratorUpdateDto updatedCurator = curatorService.updateCurator(id, curator);
        return ResponseEntity.ok(updatedCurator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuratorResponseDto> getCurator(@PathVariable Long id) {
        CuratorResponseDto curator = curatorService.getCurator(id);
        return ResponseEntity.ok(curator);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCurator(@PathVariable Long id) {
        curatorService.deleteCurator(id);
        return ResponseEntity.ok("Curator with id = " + id + " was deleted");
    }

}
