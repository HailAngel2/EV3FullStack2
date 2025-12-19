package com.example.demo.service;

import com.example.demo.model.Categoria;
import com.example.demo.model.Marca;
import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.MarcaRepository;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.dto.ProductoRequestDTO;
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
    public Producto registrarProducto(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        
        producto.setNombreProducto(dto.getNombreProducto());
        producto.setUrlImagen(dto.getUrlImagen());

        if (dto.getIdMarca() != null) {
            Marca marca = marcaRepository.findById(dto.getIdMarca())
                .orElseThrow(() -> new RecursoNoEncontradoException("Marca no encontrada con ID: " + dto.getIdMarca()));
            producto.setMarca(marca);
        }

        if (dto.getIdCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + dto.getIdCategoria()));
            producto.setCategoria(categoria);
        }

        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizarProducto(Long id, ProductoRequestDTO dto) {
        Producto productoExistente = productoRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + id));
        productoExistente.setNombreProducto(dto.getNombreProducto());
        productoExistente.setUrlImagen(dto.getUrlImagen());
        if (dto.getIdMarca() != null) {
            Marca marca = marcaRepository.findById(dto.getIdMarca())
                .orElseThrow(() -> new RecursoNoEncontradoException("Marca no encontrada con ID: " + dto.getIdMarca()));
            productoExistente.setMarca(marca);
        }
        if (dto.getIdCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + dto.getIdCategoria()));
            productoExistente.setCategoria(categoria);
        }
        return productoRepository.save(productoExistente);
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
