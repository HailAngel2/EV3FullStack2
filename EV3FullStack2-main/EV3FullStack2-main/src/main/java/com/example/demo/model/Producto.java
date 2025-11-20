package com.example.demo.model;

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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nombreProducto", length = 50,  nullable = false)
    private String nombre_Producto;

    @ManyToOne 
    @JoinColumn(name = "id_marca", nullable = true)
    private Marca marca;

    @ManyToOne 
    @JoinColumn(name = "id_categoria", nullable = true)
    private Categoria categoria;
}