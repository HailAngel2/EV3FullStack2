package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.VentaRequestDTO;
import com.example.demo.dto.DetalleVentaDTO;
import com.example.demo.dto.VentaPatchDTO;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.exception.StockException;
import com.example.demo.model.Venta;
import com.example.demo.model.DetalleVenta;
import com.example.demo.model.ENUMEstadoVenta;
import com.example.demo.model.Usuario;
import com.example.demo.model.Inventario;
import com.example.demo.repository.VentaRepository;
import com.example.demo.repository.UsuarioRepository;
import lombok.NonNull;


@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository; 
    
    @Autowired
    private InventarioService inventarioService;
    
    private static final double PORCENTAJE_IVA = 0.19; 


    @Transactional 
    public Venta registrarVenta(VentaRequestDTO ventaDTO) 
        throws RecursoNoEncontradoException, StockException {

        // --- 1. Validar y recuperar Usuario ---
        Usuario usuario = usuarioRepository.findById(ventaDTO.getIdUsuario())
                                           .orElseThrow(() -> new RecursoNoEncontradoException(
                                               "Usuario no encontrado con ID: " + ventaDTO.getIdUsuario()));

        // --- 2. Inicializar Venta y Folio ---
        Venta nuevaVenta = new Venta();
        nuevaVenta.setUsuario(usuario);
        nuevaVenta.setTipoDocumento(ventaDTO.getTipoDocumento());
        nuevaVenta.setFechaVenta(LocalDate.now());
        nuevaVenta.setFolio(this.generarNuevoFolio());
        
        List<DetalleVenta> detallesVenta = new ArrayList<>();
        double totalNetoAcumulado = 0.0;
        
        // --- 3. Procesar, Validar Stock y Calcular Detalles ---
        for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
            
            Inventario variante = inventarioService.validarStockYObtenerVariante(
                                        detalleDTO.getIdInventario(), 
                                        detalleDTO.getCantidad());
            
            DetalleVenta detalle = new DetalleVenta();
            detalle.setInventario(variante);
            detalle.setVenta(nuevaVenta);
            detalle.setCantidad(detalleDTO.getCantidad());
            
            double precioUnitario = variante.getPrecioUnitario(); 
            detalle.setPrecioVentaUnitario(precioUnitario);
            
            double subtotal = precioUnitario * detalle.getCantidad();
            detalle.setSubtotalLinea(subtotal);
            
            totalNetoAcumulado += subtotal;
            detallesVenta.add(detalle);
            
            // --- 4. Descontar Stock inmediatamente (dentro de la misma transacción) ---
            // Si falla, el @Transactional asegura el rollback de toda la operación, incluyendo el guardado de Venta.
            inventarioService.descontarStock(detalleDTO.getIdInventario(), detalleDTO.getCantidad());
        }
        
        // --- 5. Finalizar Cálculos y Asignar Totales ---
        double ivaCalculado = totalNetoAcumulado * PORCENTAJE_IVA;
        double totalFinal = totalNetoAcumulado + ivaCalculado;

        nuevaVenta.setDetalles(detallesVenta);
        nuevaVenta.setTotalNeto(totalNetoAcumulado);
        nuevaVenta.setIva(ivaCalculado);
        nuevaVenta.setTotalFinal(totalFinal);
        
        // --- 6. Guardar la Venta (con detalles) ---
        return ventaRepository.save(nuevaVenta);
    }
   
    private Long generarNuevoFolio() {
        return ventaRepository.findLastFolio()
                               .map(ultimoFolio -> ultimoFolio + 1)
                               .orElse(1L); // Si no hay folios, inicia en 1
    }

    @Transactional(readOnly = true)
    public Venta findById(@NonNull Long idVenta) throws RecursoNoEncontradoException {
        return ventaRepository.findByIdWithDetalles(idVenta) 
            .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + idVenta));    }

    @Transactional(readOnly = true)
    public Page<Venta> findAll(@NonNull Pageable pageable) {
        return ventaRepository.findAll(pageable);
    }

    @Transactional
    public void cancelarVenta(@NonNull Long idVenta) {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + idVenta));
        for (DetalleVenta detalle : venta.getDetalles()) {
            inventarioService.reponerStock(detalle.getInventario().getIdInventario(), detalle.getCantidad());
        }
        ventaRepository.delete(venta);
    }

    @Transactional
    public Venta actualizarEstadoVenta(@NonNull Long idVenta, ENUMEstadoVenta nuevoEstado) {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + idVenta));
        
        if (nuevoEstado == ENUMEstadoVenta.CANCELADA && venta.getEstado() != ENUMEstadoVenta.CANCELADA) {
            
            for (DetalleVenta detalle : venta.getDetalles()) {
                Long idInventario = detalle.getInventario().getIdInventario();
                inventarioService.reponerStock(idInventario, detalle.getCantidad());
            }
        }
        
        venta.setEstado(nuevoEstado);

        return ventaRepository.save(venta);
    }

    @Transactional
    public Venta actualizarMetadatosVenta(@NonNull Long idVenta, VentaPatchDTO patchDTO) {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + idVenta));

        // Aplicar solo si el campo viene en el DTO
        if (patchDTO.getTipoDocumento() != null) {
            venta.setTipoDocumento(patchDTO.getTipoDocumento());
        }

        if (patchDTO.getFechaVenta() != null) {
            venta.setFechaVenta(patchDTO.getFechaVenta());
        }
        return ventaRepository.save(venta);
    }
}