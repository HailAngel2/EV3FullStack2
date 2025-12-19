package com.example.demo.controller;

import com.example.demo.model.Talla;
import com.example.demo.service.TallaService;
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
@RequestMapping("/api/tallas")
public class TallaController {
    
    @Autowired
    private TallaService tallaService;

    @GetMapping
    public ResponseEntity<List<Talla>> getAll() {
        return ResponseEntity.ok(tallaService.getAllTallas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talla> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tallaService.getTallaById(id));
    }

    @PostMapping
    public ResponseEntity<Talla> create(@RequestBody Talla entity) {
        Talla nuevaTalla = tallaService.createOrUpdateTalla(entity);
        return new ResponseEntity<>(nuevaTalla, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Talla> update(@PathVariable Long id, @RequestBody Talla details) {
        tallaService.getTallaById(id); 
        
        details.setIdTalla(id);
        
        Talla actualizada = tallaService.createOrUpdateTalla(details);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tallaService.deleteTalla(id);
        return ResponseEntity.noContent().build();
    }
}