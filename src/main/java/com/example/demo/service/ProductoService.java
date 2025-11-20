package com.example.demo.service;

import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.MarcaRepository;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    
    // Dependencias para validación
    @Autowired
    private MarcaRepository marcaRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Transactional
    public Producto createOrUpdateProducto(Producto producto) {
        
        // 1. Validar existencia de la Marca
        if (producto.getMarca() != null && producto.getMarca().getId_marca() != null) {
            marcaRepository.findById(producto.getMarca().getId_marca())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "No se puede guardar el producto. Marca no encontrada con ID: " + producto.getMarca().getId_marca()));
        }

        // 2. Validar existencia de la Categoría
        if (producto.getCategoria() != null && producto.getCategoria().getId_categoria() != null) {
            categoriaRepository.findById(producto.getCategoria().getId_categoria())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "No se puede guardar el producto. Categoría no encontrada con ID: " + producto.getCategoria().getId_categoria()));
        }
        
        return productoRepository.save(producto);
    }

    @Transactional(readOnly = true)
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto getProductoById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + id));
    }
    
    @Transactional
    public void deleteProducto(Long id) {
        Producto producto = getProductoById(id);
        productoRepository.delete(producto);
    }

    @Transactional(readOnly = true)
    public List<Producto> findByNombreContaining(String nombre) {
        return productoRepository.findByNombreProductoContainingIgnoreCase(nombre);
    }

    @Transactional(readOnly = true)
    public List<Producto> findByMarca(String nombreMarca) {
        return productoRepository.findByMarcaNombreMarca(nombreMarca);
    }

    @Transactional(readOnly = true)
    public List<Producto> findByCategoria(String nombreCategoria) {
        return productoRepository.findByCategoriaNombreCategoria(nombreCategoria);
    }
}
