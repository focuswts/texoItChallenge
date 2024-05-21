package com.texoit.worstmovie.controller;

import com.texoit.worstmovie.entity.StudioEntity;
import com.texoit.worstmovie.service.StudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/studios")
@RequiredArgsConstructor
public class StudioController {

    private final StudioService studioService;

    @GetMapping
    public ResponseEntity<List<StudioEntity>> getAllStudios() {
        List<StudioEntity> studios = studioService.getStudios();
        return ResponseEntity.ok(studios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudioEntity> getStudioById(@PathVariable Long id) {
        StudioEntity studio = studioService.getStudioById(id);
        return studio != null ? ResponseEntity.ok(studio) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<StudioEntity> createStudio(@RequestBody StudioEntity studio) {
        StudioEntity createdStudio = studioService.createStudio(studio);
        return ResponseEntity.status(201).body(createdStudio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudioEntity> updateStudio(@PathVariable Long id, @RequestBody StudioEntity studioDetails) {
        StudioEntity updatedStudio = studioService.updateStudioById(id, studioDetails);
        return updatedStudio != null ? ResponseEntity.ok(updatedStudio) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudio(@PathVariable Long id) {
        studioService.deleteStudio(id);
        return ResponseEntity.noContent().build();
    }

}
