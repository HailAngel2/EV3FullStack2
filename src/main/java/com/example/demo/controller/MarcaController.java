package com.example.demo.controller;

import com.example.demo.model.Marca;
import com.example.demo.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@CrossOrigin(origins = "*")
public class MarcaController {
    
    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<Marca>> getAll() {
        return ResponseEntity.ok(marcaService.getAllMarcas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> getById(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.getMarcaById(id));
    }

    @PostMapping
    public ResponseEntity<Marca> create(@RequestBody Marca entity) {
        Marca nuevaMarca = marcaService.createOrUpdateMarca(entity);
        return new ResponseEntity<>(nuevaMarca, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> update(@PathVariable Long id, @RequestBody Marca details) {
        marcaService.getMarcaById(id); 
        
        details.setId_marca(id);
        
        Marca actualizada = marcaService.createOrUpdateMarca(details);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        marcaService.deleteMarca(id);
        return ResponseEntity.noContent().build();
    }
}