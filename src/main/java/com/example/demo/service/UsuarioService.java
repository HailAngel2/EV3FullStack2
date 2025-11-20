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
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));
    }

    @Transactional
    public Usuario createOrUpdateUsuario(Usuario usuario) {
        if (usuario.getComuna() != null && usuario.getComuna().getId_comuna() != null) {
            comunaRepository.findById(usuario.getComuna().getId_comuna())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "No se puede guardar el usuario. Comuna no encontrada con ID: " + usuario.getComuna().getId_comuna()));
        }

        if (usuario.getRegion() != null && usuario.getRegion().getId_region() != null) {
            regionRepository.findById(usuario.getRegion().getId_region())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                    "No se puede guardar el usuario. Región no encontrada con ID: " + usuario.getRegion().getId_region()));
        }
        
        // **NOTA: Aquí iría la lógica de encriptación de la contraseña antes de guardar.**
        
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deleteUsuario(Long id) {
        Usuario usuario = getUsuarioById(id);
        usuarioRepository.delete(usuario);
    }

}
