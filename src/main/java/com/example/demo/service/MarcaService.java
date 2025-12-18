package com.example.demo.service;

import com.example.demo.model.Marca;
import com.example.demo.repository.MarcaRepository;
import com.example.demo.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.NonNull;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional(readOnly = true)
    public List<Marca> getAllMarcas() {
        return marcaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Marca getMarcaById(@NonNull Long idMarca) {
        return marcaRepository.findById(idMarca)
                .orElseThrow(() -> new RecursoNoEncontradoException("Marca no encontrada con ID: " + idMarca));
    }

    @Transactional
    public Marca createOrUpdateMarca(@NonNull Marca marca) {
        return marcaRepository.save(marca);
    }

    @Transactional
    public void deleteMarca(Long idMarca) {
        Marca marca = getMarcaById(idMarca);
        marcaRepository.delete(marca);
    }
}
