package com.example.demo.controller;

import com.example.demo.dto.RegistroRequestDTO;
import com.example.demo.dto.UsuarioResponseDTO;
import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
public ResponseEntity<List<UsuarioResponseDTO>> getAll() {
    List<UsuarioResponseDTO> usuarios = usuarioService.getAllUsuarios();
    return ResponseEntity.ok(usuarios);
}

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(usuarioService.convertirADto(usuario));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@RequestBody RegistroRequestDTO registroDTO) {
        Usuario nuevoUsuario = usuarioService.registrarUsuario(registroDTO);
        return new ResponseEntity<>(usuarioService.convertirADto(nuevoUsuario), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody RegistroRequestDTO detailsDTO) {
        Usuario actualizado = usuarioService.actualizarUsuario(id, detailsDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}