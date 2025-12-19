package com.example.demo.controller;

import com.example.demo.dto.InventarioRequestDTO;
import com.example.demo.model.Inventario;
import com.example.demo.service.InventarioService;
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
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @PostMapping
    public ResponseEntity<Inventario> create(@RequestBody InventarioRequestDTO dto) {
        Inventario nuevaVariante = inventarioService.saveVariante(dto);
        return new ResponseEntity<>(nuevaVariante, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Inventario> update(@PathVariable Long id, @RequestBody InventarioRequestDTO dto) {
        Inventario actualizado = inventarioService.actualizarVariante(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping
    public ResponseEntity<List<Inventario>> getAll() {
        return ResponseEntity.ok(inventarioService.getAllVariantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> getVarianteById(@PathVariable Long id) {
        Inventario variante = inventarioService.getVarianteById(id);
        return ResponseEntity.ok(variante);
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Inventario>> getVariantesByProductId(@PathVariable Long productoId) {
        List<Inventario> variantes = inventarioService.getVariantesByProductoId(productoId);
        return ResponseEntity.ok(variantes);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariante(@PathVariable Long id) {
        inventarioService.deleteVariante(id);
        return ResponseEntity.noContent().build();
    }
}