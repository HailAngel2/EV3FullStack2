package com.example.demo.controller;

import com.example.demo.model.DetalleVenta;
import com.example.demo.service.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

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
}
