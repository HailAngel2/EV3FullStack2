package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.exception.RecursoNoEncontradoException;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin; 

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new RecursoNoEncontradoException("Credenciales inválidas.")); // Error 404/401

        boolean matches = passwordEncoder.matches(
            loginRequest.getContrasena(), 
            usuario.getContrasena()
        );

        if (matches) {
            return ResponseEntity.ok(Map.of(
                "message", "Login exitoso",
                "id", usuario.getIdUsuario(),
                "username", usuario.getUsername(),
                "rol", usuario.getRol().toString()
            ));
        } else {
            return new ResponseEntity<>("Credenciales inválidas.", HttpStatus.UNAUTHORIZED);
        }
    }
}