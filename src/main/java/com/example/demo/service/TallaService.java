package com.example.demo.service;

import com.example.demo.model.Talla;
import com.example.demo.repository.TallaRepository;
import com.example.demo.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TallaService {

    @Autowired
    private TallaRepository tallaRepository;

    @Transactional(readOnly = true)
    public List<Talla> getAllTallas() {
        return tallaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Talla getTallaById(Long id) {
        return tallaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Talla no encontrada con ID: " + id));
    }

    @Transactional
    public Talla createOrUpdateTalla(Talla talla) {
        return tallaRepository.save(talla);
    }

    @Transactional
    public void deleteTalla(Long id) {
        Talla talla = getTallaById(id);
        tallaRepository.delete(talla);
    }
}