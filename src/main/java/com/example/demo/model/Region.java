package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Region{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRegion;

    @Column(name = "region", length = 50, nullable = false)
    private String region;
    
}