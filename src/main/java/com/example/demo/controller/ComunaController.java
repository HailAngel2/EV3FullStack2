package com.example.demo.controller;

import com.example.demo.model.Comuna;
import com.example.demo.service.ComunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comunas")
@CrossOrigin(origins = "*")
public class ComunaController {
    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public List<Comuna> getAll() {
        return comunaService.getAllComunas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comuna> getById(@PathVariable Long id) {
        return comunaService.getComunaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Comuna create(@RequestBody Comuna entity) {
        return comunaService.saveComuna(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comuna> update(@PathVariable Long id, @RequestBody Comuna details) {
        return comunaService.getComunaById(id)
                .map(existing -> ResponseEntity.ok(comunaService.saveComuna(existing)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comunaService.deleteComuna(id);
        return ResponseEntity.noContent().build();
    }
}
