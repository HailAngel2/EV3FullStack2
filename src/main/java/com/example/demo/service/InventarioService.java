package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.model.Inventario;
import com.example.demo.repository.InventarioRepository;
import com.example.demo.exception.StockException;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.TallaRepository;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private TallaRepository tallaRepository;

    private static final String MSG_STOCK_INSUFICIENTE = "Stock insuficiente para la variante ID: %d. Stock disponible: %d, Solicitado: %d.";

    @Transactional(readOnly = true)
    public Inventario validarStockYObtenerVariante(Long idInventario, int cantidadSolicitada) 
        throws RecursoNoEncontradoException, StockException {

        Optional<Inventario> inventarioOpt = inventarioRepository.findById(idInventario);

        if (inventarioOpt.isEmpty()) {
            throw new RecursoNoEncontradoException("Variante de Inventario no encontrada con ID: " + idInventario);
        }

        Inventario variante = inventarioOpt.get();

        if (variante.getStock() < cantidadSolicitada) {
            String mensaje = String.format(MSG_STOCK_INSUFICIENTE, 
                                           idInventario, 
                                           variante.getStock(), 
                                           cantidadSolicitada);
            throw new StockException(mensaje);
        }

        return variante;
    }

    @Transactional
    public boolean descontarStock(Long idInventario, int cantidadVendida) {
        
        int filasActualizadas = inventarioRepository.updateStockForVenta(idInventario, cantidadVendida);
        
        if (filasActualizadas == 0) {
            Inventario variante = inventarioRepository.findById(idInventario)
                                                      .orElse(null);
            
            if (variante != null && variante.getStock() < cantidadVendida) {
                throw new StockException("Error de concurrencia: El stock se agotó antes de confirmar la venta.");
            }
            return false;
        }
        return true;
    }

    @Transactional
    public void reponerStock(Long idInventario, int cantidadAReponer) {
        inventarioRepository.reponerStockPorCancelacion(idInventario, cantidadAReponer);
    }

    @Transactional(readOnly = true)
    public Inventario getVarianteById(@NonNull Long idInventario) {
        return inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Variante de Inventario no encontrada con ID: " + idInventario));
    }

@Transactional
public Inventario saveVariante(Inventario inventario) {
    
    if (inventario.getProducto() == null || inventario.getProducto().getIdProducto() == null) {
        throw new RecursoNoEncontradoException("El objeto Inventario debe estar asociado a un Producto.");
    }
    productoRepository.findById(inventario.getProducto().getIdProducto())
        .orElseThrow(() -> new RecursoNoEncontradoException(
            "No se puede guardar la variante. Producto padre no encontrado con ID: " + inventario.getProducto().getIdProducto()));

    if (inventario.getTalla() == null || inventario.getTalla().getIdTalla() == null) {
        throw new RecursoNoEncontradoException("El objeto Inventario debe estar asociado a una Talla.");
    }
    tallaRepository.findById(inventario.getTalla().getIdTalla())
        .orElseThrow(() -> new RecursoNoEncontradoException(
            "No se puede guardar la variante. Talla no encontrada con ID: " + inventario.getTalla().getIdTalla()));

    return inventarioRepository.save(inventario);
}

    @Transactional
    public void deleteVariante(Long idInventario) {
        if (!inventarioRepository.existsById(idInventario)) {
            throw new RecursoNoEncontradoException("No se puede eliminar. Variante de Inventario no encontrada con ID: " + idInventario);
        }
        inventarioRepository.deleteById(idInventario);
    }

    @Transactional(readOnly = true)
    public List<Inventario> getVariantesByProductoId(Long idProducto) {
        return inventarioRepository.findByProductoIdProducto(idProducto); 
    }

    @Transactional(readOnly = true)
    public List<Inventario> getAllVariantes() {
        return inventarioRepository.findAll();
    }
}
