package com.example.demo.controller;

import com.example.demo.model.Comuna;
import com.example.demo.service.ComunaService;
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
@RequestMapping("/api/comunas")
public class ComunaController {
    
    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<Comuna>> getAll() {
        return ResponseEntity.ok(comunaService.getAllComunas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comuna> getById(@PathVariable Long id) {
        return ResponseEntity.ok(comunaService.getComunaById(id));
    }

    @PostMapping
    public ResponseEntity<Comuna> create(@RequestBody Comuna entity) {
        Comuna nuevaComuna = comunaService.createOrUpdateComuna(entity);
        return new ResponseEntity<>(nuevaComuna, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comuna> update(@PathVariable Long id, @RequestBody Comuna details) {
        comunaService.getComunaById(id); 
        
        details.setIdComuna(id);
        
        Comuna actualizada = comunaService.createOrUpdateComuna(details);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comunaService.deleteComuna(id);
        return ResponseEntity.noContent().build();
    }
}