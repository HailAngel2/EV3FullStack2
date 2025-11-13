package com.example.demo.controller;

import com.example.demo.model.Region;
import com.example.demo.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/regiones")
@CrossOrigin(origins = "*")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @GetMapping
    public List<Region> getAll() {
        return regionService.getAllRegions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Region> getById(@PathVariable Long id) {
        return regionService.getRegionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Region create(@RequestBody Region entity) {
        return regionService.saveRegion(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Region> update(@PathVariable Long id, @RequestBody Region details) {
        return regionService.getRegionById(id)
                .map(existing -> ResponseEntity.ok(regionService.saveRegion(existing)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        regionService.deleteRegion(id);
        return ResponseEntity.noContent().build();
    }
}
