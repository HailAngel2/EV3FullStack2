package com.example.demo.service;

import com.example.demo.model.Categoria;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Categoria getCategoriaById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + id));
    }

    @Transactional
    public Categoria createOrUpdateCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void deleteCategoria(Long id) {
        Categoria categoria = getCategoriaById(id);
        categoriaRepository.delete(categoria);
    }
}