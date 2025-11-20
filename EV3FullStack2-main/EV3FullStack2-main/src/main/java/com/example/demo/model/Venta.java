package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
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
    private Integer id_venta;

    @Column(name = "fechaVenta")
    private LocalDate fecha_venta;

    @Column(unique = true)
    private Long folio; // Número legal de la boleta (ej: 0001)

    @Enumerated(EnumType.STRING)
    private ENUMTipoDocumento tipoDocumento;

    // Ajuste importante:
    // El campo se llama "usuario" (no "id_usuario") para que coincida con mappedBy="usuario" en Usuario.java
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // Relación: una venta tiene muchos detalles
    @OneToMany(mappedBy = "venta")
    private List<DetalleVenta> detalles;

    private double totalNeto;
    private double iva;
    private double totalFinal;
}
