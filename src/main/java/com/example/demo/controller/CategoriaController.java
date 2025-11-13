package com.example.demo.controller;

import com.example.demo.model.Categoria;
import com.example.demo.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // GET - listar todas las categorías
    @GetMapping
    public List<Categoria> getAll() {
        return categoriaService.getAllCategorias();
    }

    // GET - obtener una categoría por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable Integer id) {
        return categoriaService.getCategoriaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - crear una nueva categoría
    @PostMapping
    public Categoria create(@RequestBody Categoria categoria) {
        return categoriaService.saveCategoria(categoria);
    }

    // PUT - actualizar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Integer id, @RequestBody Categoria categoriaDetails) {
        return categoriaService.getCategoriaById(id)
                .map(existing -> {
                    existing.setNombre_categoria(categoriaDetails.getNombre_categoria());
                    return ResponseEntity.ok(categoriaService.saveCategoria(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - eliminar una categoría por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoriaService.deleteCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
