package com.example.demo.service;

import com.example.demo.model.DetalleVenta;
import com.example.demo.repository.DetalleVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    public List<DetalleVenta> getAllDetalleVentas() {
        return detalleVentaRepository.findAll();
    }

    public Optional<DetalleVenta> getDetalleVentaById(@NonNull Long idDetalleVenta) {
        return detalleVentaRepository.findById(idDetalleVenta);
    }

    public DetalleVenta saveDetalleVenta(@NonNull DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

        public boolean deleteDetalleVenta(@NonNull Long idDetalleVenta) {
        if (detalleVentaRepository.existsById(idDetalleVenta)){
            detalleVentaRepository.deleteById(idDetalleVenta);
            return true;
        }
        return false;
    }
}
