package com.example.demo.dto;
import com.example.demo.model.ENUMRolUsuario;
import lombok.Data;

@Data
public class RegistroRequestDTO {
    private String username;
    private String contrasena;
    private ENUMRolUsuario rol;
    private Long idComuna; // Solo el ID
    private Long idRegion; // Solo el ID
    private String correo;
}