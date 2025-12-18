package com.example.demo.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class DetalleVentaDTO {
    
    // ID de la variante específica (Producto + Talla)
    @NotNull(message = "El ID de inventario es requerido")
    private Long idInventario;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private int cantidad;
}