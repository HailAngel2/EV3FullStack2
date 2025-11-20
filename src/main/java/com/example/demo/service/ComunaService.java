package com.example.demo.service;

import com.example.demo.model.Comuna;
import com.example.demo.repository.ComunaRepository;
import com.example.demo.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    @Transactional(readOnly = true)
    public List<Comuna> getAllComunas() {
        return comunaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Comuna getComunaById(Long id) {
        return comunaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Comuna no encontrada con ID: " + id));
    }

    @Transactional
    public Comuna createOrUpdateComuna(Comuna comuna) {
        return comunaRepository.save(comuna);
    }

    @Transactional
    public void deleteComuna(Long id) {
        Comuna comuna = getComunaById(id);
        comunaRepository.delete(comuna);
    }
}