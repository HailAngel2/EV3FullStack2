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
import lombok.NonNull;

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
        if (producto.getMarca() != null && producto.getMarca().getIdMarca() != null) {
            marcaRepository.findById(producto.getMarca().getIdMarca())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "No se puede guardar el producto. Marca no encontrada con ID: " + producto.getMarca().getIdMarca()));
        }

        // 2. Validar existencia de la Categoría
        if (producto.getCategoria() != null && producto.getCategoria().getIdCategoria() != null) {
            categoriaRepository.findById(producto.getCategoria().getIdCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "No se puede guardar el producto. Categoría no encontrada con ID: " + producto.getCategoria().getIdCategoria()));
        }
        
        return productoRepository.save(producto);
    }

    @Transactional(readOnly = true)
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto getProductoById(@NonNull Long idProducto) {
        return productoRepository.findById(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + idProducto));
    }
    
    @Transactional
    public void deleteProducto(Long idProducto) {
        Producto producto = getProductoById(idProducto);
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
