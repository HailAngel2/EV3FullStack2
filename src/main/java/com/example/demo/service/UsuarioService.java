package com.example.demo.service;

import com.example.demo.model.Comuna;
import com.example.demo.model.Region;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.ComunaRepository;
import com.example.demo.repository.RegionRepository;
import com.example.demo.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.NonNull;
import com.example.demo.dto.RegistroRequestDTO;
import com.example.demo.dto.UsuarioResponseDTO;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ComunaRepository comunaRepository;
    
    @Autowired
    private RegionRepository regionRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> getAllUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADto)
                .toList();
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioById(@NonNull Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idUsuario));
    }

    public UsuarioResponseDTO convertirADto(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setUsername(usuario.getUsername());
        dto.setCorreo(usuario.getCorreo());
        dto.setNombreRol(usuario.getRol().name());
        if (usuario.getComuna() != null) dto.setNombreComuna(usuario.getComuna().getComuna());
        if (usuario.getRegion() != null) dto.setNombreRegion(usuario.getRegion().getRegion());
        return dto;
    }

    @Transactional
    public Usuario registrarUsuario(RegistroRequestDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setUsername(dto.getUsername());
        usuario.setRol(dto.getRol());
        usuario.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuario.setCorreo(dto.getCorreo());
        actualizarRelaciones(dto, usuario);
        
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario actualizarUsuario(Long id, RegistroRequestDTO dto) {
        Usuario usuarioExistente = getUsuarioById(id); 
        usuarioExistente.setUsername(dto.getUsername());
        usuarioExistente.setRol(dto.getRol());
        usuarioExistente.setCorreo(dto.getCorreo());

        if (dto.getContrasena() != null && !dto.getContrasena().isBlank()) {
            usuarioExistente.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        }

        actualizarRelaciones(dto, usuarioExistente);

        return usuarioRepository.save(usuarioExistente);
    }

    // Método privado auxiliar para no repetir código de Comuna/Región
    private void actualizarRelaciones(RegistroRequestDTO dto, Usuario usuario) {
        if (dto.getIdComuna() != null) {
            Comuna comuna = comunaRepository.findById(dto.getIdComuna())
                .orElseThrow(() -> new RecursoNoEncontradoException("Comuna no encontrada: " + dto.getIdComuna()));
            usuario.setComuna(comuna);
        }
        if (dto.getIdRegion() != null) {
            Region region = regionRepository.findById(dto.getIdRegion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Región no encontrada: " + dto.getIdRegion()));
            usuario.setRegion(region);
        }
    }

    @Transactional
    public void deleteUsuario(Long idUsuario) {
        Usuario usuario = getUsuarioById(idUsuario);
        usuarioRepository.delete(usuario);
    }

}
