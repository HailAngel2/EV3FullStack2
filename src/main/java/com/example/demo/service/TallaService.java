package com.example.demo.service;

import com.example.demo.model.Talla;
import com.example.demo.repository.TallaRepository;
import com.example.demo.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import lombok.NonNull;

@Service
public class TallaService {

    @Autowired
    private TallaRepository tallaRepository;

    @Transactional(readOnly = true)
    public List<Talla> getAllTallas() {
        return tallaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Talla getTallaById(@NonNull Long idTalla) {
        return tallaRepository.findById(idTalla)
                .orElseThrow(() -> new RecursoNoEncontradoException("Talla no encontrada con ID: " + idTalla));
    }

    @Transactional
    public Talla createOrUpdateTalla(@NonNull Talla talla) {
        return tallaRepository.save(talla);
    }

    @Transactional
    public void deleteTalla(Long idTalla) {
        Talla talla = getTallaById(idTalla);
        tallaRepository.delete(talla);
    }
}