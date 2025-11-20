package com.example.demo.service;

import com.example.demo.model.Region;
import com.example.demo.repository.RegionRepository;
import com.example.demo.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Transactional(readOnly = true)
    public List<Region> getAllRegiones() {
        return regionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Region getRegionById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Región no encontrada con ID: " + id));
    }

    @Transactional
    public Region createOrUpdateRegion(Region region) {
        return regionRepository.save(region);
    }

    @Transactional
    public void deleteRegion(Long id) {
        Region region = getRegionById(id);
        regionRepository.delete(region);
    }
}