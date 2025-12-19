package com.example.demo.dto;

import lombok.Data;

@Data
public class ProductoRequestDTO {
    private String nombreProducto;
    private Long idMarca;
    private Long idCategoria;
    private String urlImagen;
}