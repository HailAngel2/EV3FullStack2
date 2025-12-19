package com.example.demo.dto;

import lombok.Data;

@Data
public class InventarioRequestDTO {
    private Long idProducto;
    private Long idTalla;
    private Integer stock;
    private Double precioUnitario;
}