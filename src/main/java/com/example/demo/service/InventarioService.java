package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.model.Inventario;
import com.example.demo.model.Producto;
import com.example.demo.model.Talla;
import com.example.demo.repository.InventarioRepository;
import com.example.demo.exception.StockException;
import com.example.demo.dto.InventarioRequestDTO;
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
    public Inventario saveVariante(InventarioRequestDTO dto) {
        Producto producto = productoRepository.findById(dto.getIdProducto())
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "No se pudo crear la variante: Producto ID " + dto.getIdProducto() + " no existe."));

        Talla talla = tallaRepository.findById(dto.getIdTalla())
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "No se pudo crear la variante: Talla ID " + dto.getIdTalla() + " no existe."));
        Inventario inventario = new Inventario();
        inventario.setProducto(producto);
        inventario.setTalla(talla);
        inventario.setStock(dto.getStock());
        inventario.setPrecioUnitario(dto.getPrecioUnitario());

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

    @Transactional
    public Inventario actualizarVariante(Long idInventario, InventarioRequestDTO dto) {
        Inventario inventarioExistente = inventarioRepository.findById(idInventario)
            .orElseThrow(() -> new RecursoNoEncontradoException("Inventario no encontrado con ID: " + idInventario));
        Producto producto = productoRepository.findById(dto.getIdProducto())
            .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + dto.getIdProducto()));
        inventarioExistente.setProducto(producto);
        Talla talla = tallaRepository.findById(dto.getIdTalla())
            .orElseThrow(() -> new RecursoNoEncontradoException("Talla no encontrada con ID: " + dto.getIdTalla()));
        inventarioExistente.setTalla(talla);
        inventarioExistente.setStock(dto.getStock());
        inventarioExistente.setPrecioUnitario(dto.getPrecioUnitario());
        return inventarioRepository.save(inventarioExistente);
    }
}
