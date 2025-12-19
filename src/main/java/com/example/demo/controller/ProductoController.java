package com.example.demo.controller;

import com.example.demo.dto.ProductoRequestDTO;
import com.example.demo.model.Producto;
import com.example.demo.service.ProductoService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> getAll() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    @PostMapping
    public ResponseEntity<Producto> create(@RequestBody ProductoRequestDTO productoDTO) {
        Producto nuevoProducto = productoService.registrarProducto(productoDTO);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(@PathVariable Long id, @RequestBody ProductoRequestDTO dto) {
        Producto actualizado = productoService.actualizarProducto(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> searchByNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.findByNombreContaining(nombre));
    }

    @GetMapping("/marca/{nombreMarca}")
    public ResponseEntity<List<Producto>> searchByMarca(@PathVariable String nombreMarca) {
        return ResponseEntity.ok(productoService.findByMarca(nombreMarca));
    }

    @GetMapping("/categoria/{nombreCategoria}")
    public ResponseEntity<List<Producto>> searchByCategoria(@PathVariable String nombreCategoria) {
        return ResponseEntity.ok(productoService.findByCategoria(nombreCategoria));
    }
}