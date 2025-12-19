package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(name = "nombreProducto", length = 50,  nullable = false)
    private String nombreProducto;

    @ManyToOne 
    @JoinColumn(name = "idMarca", nullable = false)
    private Marca marca;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ENUMCategoria categoria;

    @Column(name = "urlImagen", length = 512, nullable = true)
    private String urlImagen;
}