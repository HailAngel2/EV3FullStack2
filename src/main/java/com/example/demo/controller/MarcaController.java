package com.example.demo.controller;

import com.example.demo.model.Marca;
import com.example.demo.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/marcas")
@CrossOrigin(origins = "*")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public List<Marca> getAll() {
        return marcaService.getAllMarcas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> getById(@PathVariable Long id) {
        return marcaService.getMarcaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Marca create(@RequestBody Marca entity) {
        return marcaService.saveMarca(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> update(@PathVariable Long id, @RequestBody Marca details) {
        return marcaService.getMarcaById(id)
                .map(existing -> ResponseEntity.ok(marcaService.saveMarca(existing)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        marcaService.deleteMarca(id);
        return ResponseEntity.noContent().build();
    }
}
