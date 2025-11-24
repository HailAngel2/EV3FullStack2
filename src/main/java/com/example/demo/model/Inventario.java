package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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

public class Inventario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventario;

    @ManyToOne
    @JoinColumn(name = "producto")
    @JsonIgnore
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idTalla")
    private Talla talla;

    @Column(name = "stock")
    private int stock;

    @Column(name = "precioUnitario")
    private double precioUnitario;
}