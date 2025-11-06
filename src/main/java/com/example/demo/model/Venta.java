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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Venta {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_venta;

    @Column(name = "fechaVenta")
    private LocalDate fecha_venta;

    @Column(unique = true)
    private Long folio; // El número legal de la boleta (ej:0001)

    @Enumerated(EnumType.STRING)
    private ENUMTipoDocumento tipoDocumento;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario id_usuario;

    @OneToMany(mappedBy = "venta")
    private List<DetalleVenta> detalles; 

    private double totalNeto;
    private double iva;
    private double totalFinal;

}