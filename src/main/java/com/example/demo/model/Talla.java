package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Talla{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTalla;

    @Column(name = "talla", unique = true, length = 10)
    private String talla;
}