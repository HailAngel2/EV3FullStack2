package com.example.demo.dto;

import com.example.demo.model.ENUMCategoria;

import lombok.Data;

@Data
public class ProductoRequestDTO {
    private String nombreProducto;
    private Long idMarca;
    private ENUMCategoria categoria;
    private String urlImagen;
}