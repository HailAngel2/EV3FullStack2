package com.example.demo.controller;

import com.example.demo.model.Talla;
import com.example.demo.service.TallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tallas")
@CrossOrigin(origins = "*")
public class TallaController {
    @Autowired
    private TallaService tallaService;

    @GetMapping
    public List<Talla> getAll() {
        return tallaService.getAllTallas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talla> getById(@PathVariable Long id) {
        return tallaService.getTallaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Talla create(@RequestBody Talla entity) {
        return tallaService.saveTalla(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Talla> update(@PathVariable Long id, @RequestBody Talla details) {
        return tallaService.getTallaById(id)
                .map(existing -> ResponseEntity.ok(tallaService.saveTalla(existing)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tallaService.deleteTalla(id);
        return ResponseEntity.noContent().build();
    }
}
