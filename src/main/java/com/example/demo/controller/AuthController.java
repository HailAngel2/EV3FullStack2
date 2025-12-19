package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.exception.RecursoNoEncontradoException;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        Optional<Usuario> oUsuario = usuarioRepository.findByUsername(loginRequest.getUsername());
        if (oUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
        Usuario usuario = oUsuario.get();
        if (passwordEncoder.matches(loginRequest.getContrasena(), usuario.getContrasena())) {
            return ResponseEntity.ok(Map.of(
                "id", usuario.getIdUsuario(),
                "username", usuario.getUsername(),
                "rol", usuario.getRol().name()
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }
}