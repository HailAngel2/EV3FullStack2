package com.example.demo.controller;

import com.example.demo.model.Region;
import com.example.demo.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/regiones")
public class RegionController {
    
    @Autowired
    private RegionService regionService;

    @GetMapping
    public ResponseEntity<List<Region>> getAll() {
        return ResponseEntity.ok(regionService.getAllRegiones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> getById(@PathVariable Long id) {
        return ResponseEntity.ok(regionService.getRegionById(id));
    }

    @PostMapping
    public ResponseEntity<Region> create(@RequestBody Region entity) {
        Region nuevaRegion = regionService.createOrUpdateRegion(entity);
        return new ResponseEntity<>(nuevaRegion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> update(@PathVariable Long id, @RequestBody Region details) {
        regionService.getRegionById(id); 
        
        details.setIdRegion(id);
        
        Region actualizada = regionService.createOrUpdateRegion(details);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        regionService.deleteRegion(id);
        return ResponseEntity.noContent().build();
    }
}