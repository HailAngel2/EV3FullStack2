package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;

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

    //Relación correcta: un usuario puede tener muchas ventas
    @OneToMany(mappedBy = "usuario")
    private List<Venta> ventas;

    @Enumerated(EnumType.STRING)
    private ENUMRolUsuario rol;

    @ManyToOne
    @JoinColumn(name = "id_comuna")
    private Comuna comuna;

    @ManyToOne
    @JoinColumn(name = "id_region")
    private Region region;
}
