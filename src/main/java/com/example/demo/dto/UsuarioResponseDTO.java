package com.example.demo.dto;
import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long idUsuario;
    private String username;
    private String correo;
    private String nombreRol;
    private String nombreComuna;
    private String nombreRegion;
}
