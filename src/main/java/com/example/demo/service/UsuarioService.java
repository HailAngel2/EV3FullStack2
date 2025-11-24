package com.example.demo.service;

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
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioById(@NonNull Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idUsuario));
    }

    @Transactional
    public Usuario createOrUpdateUsuario(Usuario usuario) {
        
        // 1. Verificar si el usuario existe para una actualización
        boolean isNewUser = (usuario.getIdUsuario() == null);
        
        // 2. Lógica de Encriptación de Contraseña
        //    Solo encriptamos si es un nuevo usuario O si la contraseña ha sido cambiada.
        if (isNewUser || (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty())) {
            
            // Aplicar el hashing (ej. BCrypt) a la contraseña
            String hashedPassword = passwordEncoder.encode(usuario.getContrasena());
            usuario.setContrasena(hashedPassword); // Guardar el HASH
        } 
        // Nota: Si es una actualización (PUT) y la contraseña está vacía/null, 
        // Hibernate mantendrá el valor existente en la BD.

        // 3. Validación de Comuna
        if (usuario.getComuna() != null && usuario.getComuna().getIdComuna() != null) {
            comunaRepository.findById(usuario.getComuna().getIdComuna())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "No se puede guardar el usuario. Comuna no encontrada con ID: " + usuario.getComuna().getIdComuna()));
        }

        // 4. Validación de Región
        if (usuario.getRegion() != null && usuario.getRegion().getIdRegion() != null) {
            regionRepository.findById(usuario.getRegion().getIdRegion())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "No se puede guardar el usuario. Región no encontrada con ID: " + usuario.getRegion().getIdRegion()));
        }
        
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deleteUsuario(Long idUsuario) {
        Usuario usuario = getUsuarioById(idUsuario);
        usuarioRepository.delete(usuario);
    }

}
