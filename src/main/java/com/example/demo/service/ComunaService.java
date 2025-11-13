package com.example.demo.service;

import com.example.demo.model.Comuna;
import com.example.demo.repository.ComunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> getAllComunas() {
        return comunaRepository.findAll();
    }

    public Optional<Comuna> getComunaById(Long id) {
        return comunaRepository.findById(id);
    }

    public Comuna saveComuna(Comuna comuna) {
        return comunaRepository.save(comuna);
    }

    public void deleteComuna(Long id) {
        comunaRepository.deleteById(id);
    }
}
