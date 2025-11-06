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
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Column(name = "contrasenaUsuario", length = 100, nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasena;

    @Column(name = "pnombre", length = 50, nullable = true)
    private String pnombre;

    @Column(name = "snombre", length = 50, nullable = false)
    private String snombre;

    @Column(name = "appaterno", length = 50, nullable = false)
    private String appaterno;

    @Column(name = "apmaterno", length = 50, nullable = false)
    private String apmaterno;

    @OneToMany(mappedBy = "usuario")
    private List<DetalleVenta> ventas;
    
    @Enumerated(EnumType.STRING)
    private ENUMRolUsuario rol;

    @ManyToOne
    @JoinColumn(name = "id_comuna")
    private Comuna comuna;
    
    @ManyToOne
    @JoinColumn(name = "id_region")
    private Region region;
}