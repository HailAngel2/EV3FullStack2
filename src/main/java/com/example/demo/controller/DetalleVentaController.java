package com.example.demo.controller;

import com.example.demo.model.DetalleVenta;
import com.example.demo.service.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/detalleventas")
@CrossOrigin(origins = "*")
public class DetalleVentaController {
    @Autowired
    private DetalleVentaService detalleVentaService;

    @GetMapping
    public List<DetalleVenta> getAll() {
        return detalleVentaService.getAllDetalleVentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVenta> getById(@PathVariable Long id) {
        return detalleVentaService.getDetalleVentaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetalleVenta create(@RequestBody DetalleVenta entity) {
        return detalleVentaService.saveDetalleVenta(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleVenta> update(@PathVariable Long id, @RequestBody DetalleVenta details) {
        return detalleVentaService.getDetalleVentaById(id)
                .map(existing -> ResponseEntity.ok(detalleVentaService.saveDetalleVenta(existing)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        detalleVentaService.deleteDetalleVenta(id);
        return ResponseEntity.noContent().build();
    }
}
