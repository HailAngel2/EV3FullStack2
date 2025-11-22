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

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ComunaRepository comunaRepository;
    
    @Autowired
    private RegionRepository regionRepository;

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
        if (usuario.getComuna() != null && usuario.getComuna().getIdComuna() != null) {
            comunaRepository.findById(usuario.getComuna().getIdComuna())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "No se puede guardar el usuario. Comuna no encontrada con ID: " + usuario.getComuna().getIdComuna()));
        }

        if (usuario.getRegion() != null && usuario.getRegion().getIdRegion() != null) {
            regionRepository.findById(usuario.getRegion().getIdRegion())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "No se puede guardar el usuario. Región no encontrada con ID: " + usuario.getRegion().getIdRegion()));
        }
        
        // **NOTA: Aquí iría la lógica de encriptación de la contraseña antes de guardar.**
        
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deleteUsuario(Long idUsuario) {
        Usuario usuario = getUsuarioById(idUsuario);
        usuarioRepository.delete(usuario);
    }

}
