package com.example.demo.service;

import com.example.demo.model.Talla;
import com.example.demo.repository.TallaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TallaService {

    @Autowired
    private TallaRepository tallaRepository;

    public List<Talla> getAllTallas() {
        return tallaRepository.findAll();
    }

    public Optional<Talla> getTallaById(Long id) {
        return tallaRepository.findById(id);
    }

    public Talla saveTalla(Talla talla) {
        return tallaRepository.save(talla);
    }

    public void deleteTalla(Long id) {
        tallaRepository.deleteById(id);
    }
}
