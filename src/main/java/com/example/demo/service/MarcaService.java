package com.example.demo.service;

import com.example.demo.model.Marca;
import com.example.demo.repository.MarcaRepository;
import com.example.demo.exception.RecursoNoEncontradoException; // Asumiendo que existe
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional(readOnly = true)
    public List<Marca> getAllMarcas() {
        return marcaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Marca getMarcaById(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Marca no encontrada con ID: " + id));
    }

    @Transactional
    public Marca createOrUpdateMarca(Marca marca) {
        return marcaRepository.save(marca);
    }

    @Transactional
    public void deleteMarca(Long id) {
        Marca marca = getMarcaById(id);
        marcaRepository.delete(marca);
    }
}
