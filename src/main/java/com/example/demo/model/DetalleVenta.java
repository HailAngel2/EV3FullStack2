package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DetalleVenta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleVenta;
    
    @ManyToOne 
    @JoinColumn(name = "idInventario") 
    private Inventario inventario;

    @ManyToOne
    @JoinColumn(name = "idVenta")
    @JsonBackReference
    private Venta venta; 

    private int cantidad;
    
    private double precioVentaUnitario; 
    
    private double subtotalLinea; 
}