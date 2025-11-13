package com.example.demo.service;

import com.example.demo.model.DetalleVenta;
import com.example.demo.repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public List<DetalleVenta> getAllDetalleVentas() {
        return detalleVentaRepository.findAll();
    }

    public Optional<DetalleVenta> getDetalleVentaById(Long id) {
        return detalleVentaRepository.findById(id);
    }

    public DetalleVenta saveDetalleVenta(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    public void deleteDetalleVenta(Long id) {
        detalleVentaRepository.deleteById(id);
    }
}
