package com.example.demo.service;

import com.example.demo.model.Comuna;
import com.example.demo.repository.ComunaRepository;
import com.example.demo.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.NonNull;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    @Transactional(readOnly = true)
    public List<Comuna> getAllComunas() {
        return comunaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Comuna getComunaById(@NonNull Long idComuna) {
        return comunaRepository.findById(idComuna)
                .orElseThrow(() -> new RecursoNoEncontradoException("Comuna no encontrada con ID: " + idComuna));
    }

    @Transactional
    public Comuna createOrUpdateComuna(@NonNull Comuna comuna) {
        return comunaRepository.save(comuna);
    }

    @Transactional
    public void deleteComuna(Long idComuna) {
        Comuna comuna = getComunaById(idComuna);
        comunaRepository.delete(comuna);
    }
}